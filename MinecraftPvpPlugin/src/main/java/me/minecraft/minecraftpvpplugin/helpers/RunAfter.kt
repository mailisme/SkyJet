package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit
import kotlin.math.roundToLong

class RunAfter(seconds: Double, task: Runnable) {
    private var timerIndex =
        Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPvpPlugin.instance, task, (seconds * 20).roundToLong())

    fun cancel() {
        Bukkit.getScheduler().cancelTask(timerIndex)
    }
}