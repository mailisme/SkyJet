package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.Countdown
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import org.bukkit.World

class GameLoop(var world: World, private val pvpTime: Double, private val noPvpTime: Double) {
    private lateinit var setPvp: RunEveryFor
    private lateinit var setNoPvp: RunEveryFor

    fun start() {
        world.pvp = false

        setPvp = RunEveryFor(pvpTime + noPvpTime, pvpTime - 3) {
            object : Countdown(world.players, "PVP will start in", "START") {
                override fun onCountdownEnd() {
                    world.pvp = true
                }
            }
        }

        setNoPvp = RunEveryFor(pvpTime + noPvpTime, pvpTime + noPvpTime - 3) {
            object : Countdown(world.players, "PVP will end in", "SEARCH FOR GADGETS!") {
                override fun onCountdownEnd() {
                    world.pvp = false
                }
            }
        }
    }

    fun stop() {
        setPvp.cancel()
        setNoPvp.cancel()
    }
}
