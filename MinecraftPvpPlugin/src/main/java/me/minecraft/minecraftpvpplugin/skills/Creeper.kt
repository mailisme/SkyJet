package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerToggleSneakEvent

object Creeper : Skill(Material.MONSTER_EGG, "苦力怕", coolDownSeconds = 60.0, switchLike = true, lore = listOf(
    "${ChatColor.YELLOW}在對手8格外蹲下隱形，接近對手放開爆炸",
    "${ChatColor.GRAY}冷卻時間：60秒",
    "${ChatColor.GRAY}蹲下後20秒未放開也會爆炸",
    "${ChatColor.GRAY}爆炸不會傷害施放者"
)) {
    private val invinciblePlayers = mutableListOf<Player>()

    @EventHandler
    fun handleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player

        // If start sneaking
        if (event.isSneaking) {
            if (!super.hasSkill(player)) return

            LogWriter.log("${player.name} use 苦力怕")

            val distanceBetweenOpponent = player.location.distance(PvpPlaceManager.getOpponent(player)!!.location)

            if (distanceBetweenOpponent < 8) {
                player.sendMessage("對手距離 $distanceBetweenOpponent 格 < 8 格 無法使用技能")
                return
            }

            if (!super.isTriggerActivateSuccessful(player)) return

            player.sendMessage("放開以爆炸")
            player.addPotionEffect(Effects.invisible)
            player.inventory.helmet = null
            player.inventory.chestplate = null
            player.inventory.leggings = null
            player.inventory.boots = null

            RunAfter(20.0) {
                explode(player)
            }
        } else {
            explode(player)
        }
    }

    @EventHandler
    fun handleDamage(event: EntityDamageEvent) {
        if (event.entityType != EntityType.PLAYER) return
        if (!invinciblePlayers.contains(event.entity as Player)) return
        event.isCancelled = true
    }

    private fun explode(player: Player) {
        if (!super.isTriggerDeactivateSuccessful(player)) return
        LogWriter.log("${player.name} use 苦力怕 爆")

        player.removePotionEffect(Effects.invisible.type)
        player.inventory.helmet = Items.ironHelmet
        player.inventory.chestplate = Items.ironChestplate
        player.inventory.leggings = Items.ironLeggings
        player.inventory.boots = Items.ironBoots

        invinciblePlayers.add(player)
        RunAfter(3.0) {
            invinciblePlayers.remove(player)
        }

        player.world.createExplosion(player.location.x, player.location.y, player.location.z, 10f, false, false)
    }
}