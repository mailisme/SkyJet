package me.minecraft.minecraftpvpplugin

import org.bukkit.entity.Player
import java.util.*

open class Countdown(player: Player,
                     countdownMessage: String,
                     countdownEndMessage: String,
                     countdownEndSubMessage: String = ":D") {
    init {
        val countdown: TimerTask = object : TimerTask() {
            var leftSeconds: Int = 3
            override fun run() {
                player.sendTitle(countdownMessage, leftSeconds.toString())
                onCountdown()

                if (leftSeconds == 0) {
                    player.sendTitle(countdownEndMessage, countdownEndSubMessage)
                    onCountdownEnd()
                    this.cancel()
                }

                leftSeconds -= 1
            }
        }

        val timer = Timer()
        timer.schedule(countdown, 0, 1000)
    }

    open fun onCountdown() {}
    open fun onCountdownEnd() {}
}