package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit
import kotlin.math.roundToLong
import kotlin.random.Random

// This code will take in a Runnable "task" and run it every random seconds (between "minIntervalSeconds" and "maxIntervalSeconds").

class RunAperiodically(
    private val minIntervalSeconds: Double,
    private val maxIntervalSeconds: Double,
    private val task: Runnable
) {
    private var currTimerIndex = 0

    private val run = Runnable {
        currTimerIndex = scheduleNextTask()
        task.run()
    }

    private fun scheduleNextTask(): Int {
        val timerIndex = Bukkit.getScheduler().scheduleSyncDelayedTask(
            MinecraftPvpPlugin.instance,
            run,
            Random.nextLong((minIntervalSeconds * 20).roundToLong(), (maxIntervalSeconds * 20).roundToLong())
        )
        return timerIndex
    }

    init {
        scheduleNextTask()
    }

    fun cancel() {
        Bukkit.getScheduler().cancelTask(currTimerIndex)
    }
}