package me.minecraft.minecraftpvpplugin

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.function.Consumer

class PvpPlace(world: World) {
    val playerSlots = mutableListOf<Player?>(null, null)
    val gameLoop = GameLoop(world, 30, 30)
    val randomSpawnGadget = RandomSpawnGadget(world)
}

object PvpPlaceManager : Listener {
    private var pvpPlaces: MutableMap<World, PvpPlace> = buildMap {
        for (world in Worlds.pvpWorlds) {
            put(world, PvpPlace(world))
        }
    }.toMutableMap()

    // Adds Player to players list, and teleports them to the right world.
    fun addPlayer(player: Player) {
        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots

            if (playerSlots[1] == null && playerSlots[0] != null) {
                player.teleport(Location(world, 118.5, 98.0, 84.5, 180f, 0f))
                playerSlots[1] = player
                startGame(world)
                MinecraftPvpPlugin.onPlayerToPvp(player)
                return
            }
        }

        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots

            if (playerSlots[0] == null) {
                player.teleport(Location(world, 118.5, 98.0, 54.5))
                playerSlots[0] = player
                MinecraftPvpPlugin.onPlayerToPvp(player)
                return
            } else if (playerSlots[1] == null) {
                player.teleport(Location(world, 118.5, 98.0, 84.5, 180f, 0f))
                playerSlots[1] = player
                MinecraftPvpPlugin.onPlayerToPvp(player)
                startGame(world)
                return
            }
        }

        player.sendMessage("Server is full :(")
    }

    private fun startGame(world: World) {
        val place = pvpPlaces[world]!!
        val playerSlots = place.playerSlots

        object : Countdown(world.players, "The game will start in", "SEARCH FOR GADGETS!") {
            override fun onCountdown() {
                playerSlots[0]!!.teleport(Location(world, 118.5, 98.0, 54.5))
                playerSlots[1]!!.teleport(Location(world, 118.5, 98.0, 84.5, 180f, 0f))
            }

            override fun onCountdownEnd() {
                MinecraftPvpPlugin.onPlayerToPvp(playerSlots[0])
                MinecraftPvpPlugin.onPlayerToPvp(playerSlots[1])

                place.gameLoop.start()
                place.randomSpawnGadget.start()
            }
        }
    }


    // Marks input Player as loser, and the opponent of the Player as winner. Remove them from the players list and teleport them back to lobby.
    fun removePlayer(player: Player) {
        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots

            for (playerIndex in playerSlots.indices) {
                if (playerSlots[playerIndex] === player) {
                    val anotherPlayerIndex = if (playerIndex == 0) {
                        1
                    } else {
                        0
                    }

                    val anotherPlayer = playerSlots[anotherPlayerIndex]


                    player.teleport(Locations.lobbySpawn)
                    MinecraftPvpPlugin.onPlayerToLobby(player)

                    if (anotherPlayer != null) {
                        onPlayerLose(player)
                        onPlayerWin(anotherPlayer)

                        anotherPlayer.teleport(Locations.lobbySpawn)
                        MinecraftPvpPlugin.onPlayerToLobby(anotherPlayer)
                    }

                    playerSlots[0] = null
                    playerSlots[1] = null

                    world.entities.forEach(Consumer { e: Entity ->
                        if (e is Item) {
                            e.remove()
                        }
                    })

                    place.randomSpawnGadget.stop()
                    place.gameLoop.stop()
                }
            }
        }
    }

    private fun onPlayerLose(player: Player?) {
        player?.sendTitle(ChatColor.AQUA.toString() + "You Lose", ChatColor.DARK_BLUE.toString() + ":(")
    }

    private fun onPlayerWin(player: Player?) {
        player?.sendTitle(ChatColor.GOLD.toString() + "You Win !!", ChatColor.RED.toString() + ":D")
    }
}
