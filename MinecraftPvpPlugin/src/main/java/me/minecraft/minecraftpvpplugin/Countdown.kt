package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

open class Countdown(
    players: List<Player>,
    countdownMessage: String,
    countdownEndMessage: String,
    countdownEndSubMessage: String = ":D"
) {
    private var counterIndex = 0

    init {
        var leftSeconds = 3

        val countdown = Runnable {
            players.forEach{ it.sendTitle(countdownMessage, leftSeconds.toString()) }
            onCountdown()

            if (leftSeconds <= 0) {
                players.forEach { it.sendTitle(countdownEndMessage, countdownEndSubMessage) }
                onCountdownEnd()
                print(counterIndex)
                Bukkit.getServer().scheduler.cancelTask(counterIndex)
            }

            leftSeconds -= 1
        }

        counterIndex = Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(MinecraftPvpPlugin.instance, countdown, 0, 20)
    }

    open fun onCountdown() {}
    open fun onCountdownEnd() {}
}