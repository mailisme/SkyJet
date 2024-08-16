package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.Countdown
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.refs.Locations
import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import kotlin.math.pow

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

        for ((world, place) in pvpPlaces) {
            val playerSlots = place.playerSlots

            if (playerSlots[1] == null && playerSlots[0] != null) {
                player.teleport(Locations.PvpSpawn1(world))
                playerSlots[1] = PvpPlayer(player, skill)
                startGame(world)
                MinecraftPvpPlugin.onPlayerToPvp(player, skill)
                return
            }
        }

        for ((world, place) in pvpPlaces) {
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
        val (world, place, _) = getPvpPlacePlayerByPlayer(player)

        val playerSlots = place!!.playerSlots

        val anotherPlayer = getOpponent(player)

        if (anotherPlayer != null) {
            onPlayerWin(anotherPlayer)
            onPlayerLose(player)
        }

        world!!.pvp = false

        RunAfter(1.0) {
            totalPlayerCount -= if (anotherPlayer != null) 2 else 1

            player.let { MinecraftPvpPlugin.onPlayerToLobby(it) }
            anotherPlayer?.let { MinecraftPvpPlugin.onPlayerToLobby(it) }

            if (reason == "kill") {
                LogWriter.LogWriter("${player.name} was killed by ${anotherPlayer?.name}.\n")

                if (anotherPlayer != null) {
                    DataManager.addInt(anotherPlayer, "kill", 1)
                    val kills = DataManager.get(anotherPlayer, "kill")
                    val level = kills.toFloat().pow(0.6F).toInt()
                    DataManager.set(anotherPlayer, "level", "$level")
                    MinecraftPvpPlugin.mainScoreboard.renderScoreboard(anotherPlayer)
                    MinecraftPvpPlugin.customTag.updateTag(anotherPlayer)
                }
            }

            else {
                LogWriter.LogWriter("${player.name} leave the game.\n")
            }

            playerSlots[0] = null
            playerSlots[1] = null

            for (e in world.entities) {
                if (e is Item) {
                    e.remove()
                }
            }

            if (place.started) {
                place.randomSpawnGadget.stop()
                place.gameLoop.stop()
                place.started = false
            }

            world.pvp = true
        }
    }

    private fun onPlayerLose(player: Player) {
        player.resetTitle()
        player.sendTitle(ChatColor.AQUA.toString() + "You Lose", ChatColor.DARK_BLUE.toString() + ":(")
    }

    private fun onPlayerWin(player: Player) {
        player.resetTitle()
        player.sendTitle(ChatColor.GOLD.toString() + "You Win !!", ChatColor.RED.toString() + ":D")
    }

    fun getPlayerSkill(player: Player): Skill? {
        val (_, _, pvpPlayer) = getPvpPlacePlayerByPlayer(player)
        return pvpPlayer?.skill
    }

    private fun getPvpPlacePlayerByPlayer(player: Player): Triple<World?, PvpPlace?, PvpPlayer?> {
        for ((world, pvpPlace) in pvpPlaces) {
            for (playerSlot in pvpPlace.playerSlots) {
                if (playerSlot?.player == player) {
                    return Triple(world, pvpPlace, playerSlot);
                }
            }
        }

        return Triple(null, null, null)
    }

    fun getOpponent(player: Player): Player? {
        val (_, place, _) = getPvpPlacePlayerByPlayer(player)

        for (opponentPlayerSlot in place!!.playerSlots) {
            if (opponentPlayerSlot?.player != player && opponentPlayerSlot?.player != null) {
                return opponentPlayerSlot.player
            }
        }

        return null
    }
}
