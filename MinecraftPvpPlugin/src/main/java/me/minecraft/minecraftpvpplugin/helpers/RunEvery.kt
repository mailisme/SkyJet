package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit
import kotlin.math.roundToLong

class RunEvery(seconds: Double, after: Double = 0.0, task: Runnable) {
    private var timerIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(
        MinecraftPvpPlugin.instance,
        task,
        (after * 20).roundToLong(),
        (seconds * 20).roundToLong()
    )

    fun cancel() {
        Bukkit.getScheduler().cancelTask(timerIndex)
    }
}