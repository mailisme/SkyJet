package me.minecraft.minecraftpvpplugin.helpers

import org.bukkit.entity.Player

open class Countdown(
    players: List<Player>,
    countdownMessage: String,
    countdownEndMessage: String,
    countdownEndSubMessage: String = ":D"
) {
    private var leftSeconds = 3

    private var counter = RunEveryFor(1.0) {
        for (player in players) player.sendTitle(countdownMessage, leftSeconds.toString())
        onCountdown()

        if (leftSeconds <= 0) {
            for (player in players) player.sendTitle(countdownEndMessage, countdownEndSubMessage)
            onCountdownEnd()
            cancelCounter()
        }

        leftSeconds -= 1
    }

    private fun cancelCounter() {
        counter.cancel()
    }

    open fun onCountdown() {}
    open fun onCountdownEnd() {}
}