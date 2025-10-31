package me.minecraft.minecraftpvpplugin.discord_bot.commands

import me.jakejmattson.discordkt.commands.commands

class ping {
    fun ping() =commands("ping") {
                    slash("ping", "HAHAHAHAHA") {
                        execute {
                            respond("pong")
                        }
                    }
                    }
}