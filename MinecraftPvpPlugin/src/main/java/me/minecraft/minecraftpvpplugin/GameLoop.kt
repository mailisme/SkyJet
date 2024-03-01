package me.minecraft.minecraftpvpplugin

import org.bukkit.World
import java.sql.Time
import java.util.*

class GameLoop(var world: World, private val pvpTime: Long, private val noPvpTime: Long) {
    private lateinit var timer: Timer


    fun start() {
        timer = Timer()

        world.pvp = false

        val setPvp: TimerTask = object : TimerTask() {
            override fun run() {
                object : Countdown(world.players, "PVP will start in", "START") {
                    override fun onCountdownEnd() {
                        world.pvp = true
                    }
                }
            }
        }

        val setNoPvp: TimerTask = object : TimerTask() {
            override fun run() {
                object : Countdown(world.players, "PVP will end in", "SEARCH FOR GADGETS!") {
                    override fun onCountdownEnd() {
                        world.pvp = false
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
