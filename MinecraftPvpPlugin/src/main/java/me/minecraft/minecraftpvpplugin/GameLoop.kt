package me.minecraft.minecraftpvpplugin

import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

class GameLoop(var world: World, private val pvpTime: Long, private val noPvpTime: Long) {
    private val timer = Timer()

    fun start() {
        world.pvp = false

        val setPvp: TimerTask = object : TimerTask() {
            override fun run() {
                world.players.forEach {
                    object : Countdown(it, "PVP will start in", "START") {
                        override fun onCountdownEnd() {
                            world.pvp = true
                        }
                    }
                }
            }
        }

        val setNoPvp: TimerTask = object : TimerTask() {
            override fun run() {
                world.players.forEach {
                    object : Countdown(it, "PVP will end in", "SEARCH FOR GADGETS!") {
                        override fun onCountdownEnd() {
                            world.pvp = false
                        }
                    }
                }
            }
        }

        timer.schedule(setPvp, (pvpTime - 3) * 1000, (pvpTime + noPvpTime) * 1000)
        timer.schedule(setNoPvp, (pvpTime + noPvpTime - 3) * 1000, (pvpTime + noPvpTime) * 1000)
    }

    fun stop() {
        timer.cancel()
    }
}
