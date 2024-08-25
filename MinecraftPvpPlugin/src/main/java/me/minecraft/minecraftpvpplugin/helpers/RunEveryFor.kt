package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit
import kotlin.math.roundToLong

class RunEveryFor(seconds: Double, after: Double = 0.0, times: Int? = null, start: Float = 0f, end: Float? = times?.toFloat(), step: Int = 1, task: (Float) -> Unit) {
    private var i: Float = start

    private var timerIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(
        MinecraftPvpPlugin.instance,
        {
            task(i)
            i += step

            if (end != null && i >= end) cancel()
        },
        (after * 20).roundToLong(),
        (seconds * 20).roundToLong()
    )

    fun cancel() {
        Bukkit.getScheduler().cancelTask(timerIndex)
    }
}