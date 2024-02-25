package me.minecraft.minecraftpvpplugin

import org.bukkit.*
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.*
import java.util.function.Consumer

object PvpPlace : Listener {
    // Two lists so that the Players with the index 0 is at PVP0 world.
    var world_of_players: Array<Array<Player?>> = Array(3) { arrayOfNulls(2) }

    var randomSpawnGadget: RandomSpawnGadget = RandomSpawnGadget()
    var gameLoop: GameLoop = GameLoop()

    // Adds Player to players list, and teleports them to the right world.
    fun AddPlayer(player: Player) {
        var FoundEmptyPlayerSlot = false

        for (WorldIndex in world_of_players.indices) {
            val PVPWorld: World = MinecraftPvpPlugin.Companion.PVPWorlds.get(WorldIndex)

            if (world_of_players[WorldIndex][1] == null && world_of_players[WorldIndex][0] != null) {
                player.teleport(Location(PVPWorld, 118.5, 98.0, 84.5, 180f, 0f))
                world_of_players[WorldIndex][1] = player
                GameStart(WorldIndex)
                FoundEmptyPlayerSlot = true
                break
            }
        }

        if (!FoundEmptyPlayerSlot) {
            for (WorldIndex in world_of_players.indices) {
                val PVPWorld: World = MinecraftPvpPlugin.Companion.PVPWorlds.get(WorldIndex)

                if (world_of_players[WorldIndex][0] == null) {
                    player.teleport(Location(PVPWorld, 118.5, 98.0, 54.5))
                    world_of_players[WorldIndex][0] = player
                    FoundEmptyPlayerSlot = true
                    break
                } else if (world_of_players[WorldIndex][1] == null) {
                    player.teleport(Location(PVPWorld, 118.5, 98.0, 84.5, 180f, 0f))
                    world_of_players[WorldIndex][1] = player
                    GameStart(WorldIndex)
                    FoundEmptyPlayerSlot = true
                    break
                }
            }
        }

        if (!FoundEmptyPlayerSlot) {
            player.sendMessage("Server is full :(")
        } else {
            MinecraftPvpPlugin.Companion.ToPVP(player)
        }
    }

    fun GameStart(WorldIndex: Int) {
        val PVPWorld: World = MinecraftPvpPlugin.Companion.PVPWorlds.get(WorldIndex)

        randomSpawnGadget.AddSpawnerToWorld(PVPWorld)

        val CountDown: TimerTask = object : TimerTask() {
            var LeftSeconds: Int = 3
            override fun run() {
                world_of_players[WorldIndex][0]!!.teleport(Location(PVPWorld, 118.5, 98.0, 54.5))
                world_of_players[WorldIndex][1]!!.teleport(Location(PVPWorld, 118.5, 98.0, 84.5, 180f, 0f))

                world_of_players[WorldIndex][0]!!.sendTitle("The game will start in", LeftSeconds.toString())
                world_of_players[WorldIndex][1]!!.sendTitle("The game will start in", LeftSeconds.toString())

                if (LeftSeconds == 0) {
                    world_of_players[WorldIndex][0]!!.sendTitle("SEARCH FOR GADGETS!", ":D")
                    world_of_players[WorldIndex][1]!!.sendTitle("SEARCH FOR GADGETS!", ":D")

                    MinecraftPvpPlugin.Companion.ToPVP(world_of_players[WorldIndex][0])
                    MinecraftPvpPlugin.Companion.ToPVP(world_of_players[WorldIndex][1])

                    gameLoop.AddGameLoopToWorld(PVPWorld)

                    this.cancel()
                }

                LeftSeconds -= 1
            }
        }

        val t = Timer()
        t.schedule(CountDown, 0, 1000)
    }


    // Marks input Player as loser, and the opponent of the Player as winner. Remove them from the players list and teleport them back to lobby.
    fun RemovePlayer(player: Player?) {
        for (WorldIndex in world_of_players.indices) {
            for (PlayerIndex in 0..1) {
                if (world_of_players[WorldIndex][PlayerIndex] === player) {
                    var AnotherPlayerIndex = if (PlayerIndex == 0) {
                        1
                    } else {
                        0
                    }

                    val AnotherPlayer = world_of_players[WorldIndex][AnotherPlayerIndex]

                    if (player != null) {
                        player.teleport(Locations.lobby)
                        MinecraftPvpPlugin.Companion.ToLobby(player)
                    }

                    if (AnotherPlayer != null) {
                        Lose(player)
                        Win(AnotherPlayer)

                        AnotherPlayer.teleport(Locations.lobby)
                        MinecraftPvpPlugin.Companion.ToLobby(AnotherPlayer)
                    }

                    world_of_players[WorldIndex][0] = null
                    world_of_players[WorldIndex][1] = null

                    val world: World = MinecraftPvpPlugin.Companion.PVPWorlds.get(WorldIndex)

                    randomSpawnGadget.DeleteSpawnerFromWorld(world)
                    gameLoop.DeleteGameLoopFromWorld(world)

                    world.entities.forEach(Consumer { e: Entity ->
                        if (e is Item) {
                            e.remove()
                        }
                    })
                }
            }
        }
    }

    fun Lose(player: Player?) {
        player?.sendTitle(ChatColor.AQUA.toString() + "You Lose", ChatColor.DARK_BLUE.toString() + ":(")
    }

    fun Win(player: Player?) {
        player?.sendTitle(ChatColor.GOLD.toString() + "You Win !!", ChatColor.RED.toString() + ":D")
    }
}
