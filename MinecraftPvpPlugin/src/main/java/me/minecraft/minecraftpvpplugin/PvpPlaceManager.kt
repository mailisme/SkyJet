package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin.Companion.lobbyScoreboard
import me.minecraft.minecraftpvpplugin.helpers.Countdown
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.refs.Locations
import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.function.Consumer
import kotlin.math.pow

// TODO: MAKE THE PLAYER DATA STRUCTURE BETTER
class PvpPlayer(val player: Player, val skill: Skill)

class PvpPlace(world: World) {
    val playerSlots = mutableListOf<PvpPlayer?>(null, null)
    var started = false
    val gameLoop = GameLoop(world, 30.0, 30.0)
    val randomSpawnGadget = RandomSpawnGadget(world)
}

object PvpPlaceManager {
    private var pvpPlaces = buildMap {
        for (world in Worlds.pvpWorlds) {
            put(world, PvpPlace(world))
        }
    }.toMutableMap()

    private var totalPlayerCount = 0;

    // Adds Player to players list, and teleports them to the right world.
    fun addPlayer(player: Player, skill: Skill) {
        if (totalPlayerCount >= pvpPlaces.count() * 2) {
            val world = Worlds.newPvpWorld()
            pvpPlaces[world] = PvpPlace(world)
        }

        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots

            if (playerSlots[1] == null && playerSlots[0] != null) {
                player.teleport(Locations.PvpSpawn1(world))
                playerSlots[1] = PvpPlayer(player, skill)
                startGame(world)
                MinecraftPvpPlugin.onPlayerToPvp(player, skill)
                return
            }
        }

        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots

            if (playerSlots[0] == null) {
                player.teleport(Locations.PvpSpawn0(world))
                playerSlots[0] = PvpPlayer(player, skill)
                MinecraftPvpPlugin.onPlayerToPvp(player, skill)
                return
            } else if (playerSlots[1] == null) {
                player.teleport(Locations.PvpSpawn1(world))
                playerSlots[1] = PvpPlayer(player, skill)
                MinecraftPvpPlugin.onPlayerToPvp(player, skill)
                startGame(world)
                return
            }
        }
    }

    fun startGame(world: World) {
        val place = pvpPlaces[world]!!
        val playerSlots = place.playerSlots
        LogWriter.LogWriter(playerSlots[0]?.player?.name+"fighting w/ "+playerSlots[1]?.player?.name+"\n")

        object : Countdown(world.players, "The game will start in", "SEARCH FOR GADGETS!") {
            override fun onCountdown() {
                playerSlots[0]?.player?.teleport(Location(world, 118.5, 98.0, 54.5))
                playerSlots[1]?.player?.teleport(Location(world, 118.5, 98.0, 84.5, 180f, 0f))
            }

            override fun onCountdownEnd() {
                playerSlots[0]?.let { MinecraftPvpPlugin.onPlayerToPvp(it.player, it.skill) }
                playerSlots[1]?.let { MinecraftPvpPlugin.onPlayerToPvp(it.player, it.skill) }
                place.gameLoop.start()
                place.randomSpawnGadget.start()
                place.started = true
            }
        }
    }


    // Marks input Player as loser, and the opponent of the Player as winner. Remove them from the players list and teleport them back to lobby.
    fun removePlayer(player: Player, reason: String) {
        pvpPlaces.forEach { (world, place) ->
            val playerSlots = place.playerSlots
            val scoreboard = MinecraftPvpPlugin.lobbyScoreboard

            for (playerIndex in playerSlots.indices) {
                if (playerSlots[playerIndex]?.player === player) {
                    val anotherPlayer = getOpponent(player)

                    RunAfter(1.0) {
                        totalPlayerCount --
                        player.teleport(Locations.lobbySpawn)
                        MinecraftPvpPlugin.onPlayerToLobby(player)

                        if (anotherPlayer != null) {
                            onPlayerWin(anotherPlayer)
                            onPlayerLose(player)

                            totalPlayerCount --
                            anotherPlayer.teleport(Locations.lobbySpawn)
                            MinecraftPvpPlugin.onPlayerToLobby(anotherPlayer)
                        }

                        if (reason == "kill") {
                            LogWriter.LogWriter("${player.name} was killed by ${anotherPlayer?.name}.\n")

                            if (anotherPlayer != null) scoreboard.increaseScoreboardInt(anotherPlayer, "kill", 1)
                            val a =  anotherPlayer?.let { lobbyScoreboard.getScoreboard(it, "kill").toFloat().pow(x=0.6F) }
                                ?.toInt()
                            anotherPlayer?.let { lobbyScoreboard.changeScoreboard(it, "level", (a!!).toString()) }
                        }

                        else {
                            LogWriter.LogWriter("${player.name} leave the game.\n")
                        }

                        playerSlots[0] = null
                        playerSlots[1] = null

                        world.entities.forEach(Consumer { e: Entity ->
                            if (e is Item) {
                                e.remove()
                            }
                        })

                        if (place.started) {
                            place.randomSpawnGadget.stop()
                            place.gameLoop.stop()
                            place.started = false
                        }
                    }
                }
            }
        }
    }

    private fun onPlayerLose(player: Player) {
        player.sendTitle(ChatColor.AQUA.toString() + "You Lose", ChatColor.DARK_BLUE.toString() + ":(")
    }

    private fun onPlayerWin(player: Player) {
        player.sendTitle(ChatColor.GOLD.toString() + "You Win !!", ChatColor.RED.toString() + ":D")
    }

    fun getPlayerSkill(player: Player): Skill? {
        for ((world, pvpPlace) in pvpPlaces) {
            for (playerSlot in pvpPlace.playerSlots) {
                if (playerSlot?.player == player) {
                    return playerSlot.skill
                }
            }
        }

        return null
    }

    fun getOpponent(player: Player): Player? {
        for ((world, pvpPlace) in pvpPlaces) {
            for (playerSlot in pvpPlace.playerSlots) {
                if (playerSlot?.player == player) {
                    for (opponentPlayerSlot in pvpPlace.playerSlots) {
                        if (opponentPlayerSlot?.player != player && opponentPlayerSlot?.player != null) {
                            return opponentPlayerSlot.player
                        }
                    }
                }
            }
        }

        return null
    }
}
