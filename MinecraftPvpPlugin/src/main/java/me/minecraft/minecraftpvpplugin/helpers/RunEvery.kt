package me.minecraft.minecraftpvpplugin.helpers

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit

class RunEvery(seconds: Long, after: Long = 0, task: Runnable) {
    private var timerIndex = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPvpPlugin.instance, task, after * 20, seconds * 20)
    fun cancel() {
        Bukkit.getScheduler().cancelTask(timerIndex)
    }
}