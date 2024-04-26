package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit

class RunAfter(seconds: Long, task: Runnable) {
    private var timerIndex = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPvpPlugin.instance, task, seconds * 20)

    fun cancel() {
        Bukkit.getScheduler().cancelTask(timerIndex)
    }
}