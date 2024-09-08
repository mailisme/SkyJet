package me.minecraft.minecraftpvpplugin.bot

import net.minecraft.server.v1_8_R3.*
import org.bukkit.entity.Player
import kotlin.collections.HashMap

object BotManager {
    private val playerBotNameBotMap = hashMapOf<Player, HashMap<String, EntityLiving>>()

    fun addBot(player: Player, name: String, entity: EntityLiving) {
        if (!playerBotNameBotMap.containsKey(player)) playerBotNameBotMap[player] = hashMapOf()
        playerBotNameBotMap[player]!![name] = entity
    }

    fun getBot(player: Player, name: String): EntityLiving {
        return playerBotNameBotMap[player]!![name]!!
    }
    fun hasBot(player: Player, name: String): Boolean {
        return playerBotNameBotMap.containsKey(player) && playerBotNameBotMap[player]!!.containsKey(name)
    }
}