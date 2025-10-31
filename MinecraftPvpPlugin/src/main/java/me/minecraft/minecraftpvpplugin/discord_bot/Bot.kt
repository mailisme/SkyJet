package me.minecraft.minecraftpvpplugin.discord_bot
import me.jakejmattson.discordkt.arguments.IntegerArg
import me.jakejmattson.discordkt.commands.commands
import me.jakejmattson.discordkt.dsl.bot
import java.io.File

fun botMain() {
    val file = File("token.txt")

    bot(file.readText().trim()) {
        prefix { "+" }
    }
    return
}

