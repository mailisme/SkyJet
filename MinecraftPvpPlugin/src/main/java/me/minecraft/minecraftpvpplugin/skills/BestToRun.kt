package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.effect.CustomEffect
import me.minecraft.minecraftpvpplugin.helpers.LogWriter
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.effect.InGaussian
import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector


object BestToRun : Skill(Material.BONE, "走為上策", lore = listOf(
    "${ChatColor.YELLOW}隨血量降低，加強裝備保護，但減弱攻擊",
    "${ChatColor.GRAY}血量10~15: 鑽胸 虛弱Ⅰ",
    "${ChatColor.GRAY}血量05~10: 鑽胸 鑽腿 虛弱Ⅱ",
    "${ChatColor.GRAY}血量00~05: 全套鑽裝 虛弱Ⅲ"
)) {
    @EventHandler
    fun handleDamage(event: EntityDamageEvent) {
        if (event.entityType != EntityType.PLAYER) return

        val player: Player = event.entity as Player

        if (!super.isTriggerActivateSuccessful(player)) return

        val healthAfterDamage = player.health - event.finalDamage

        var prevEffectLevel = -1 // -1 means no weakness effect

        for (potionEffect in player.activePotionEffects) {
            if (potionEffect.type == PotionEffectType.WEAKNESS) {
                prevEffectLevel = potionEffect.amplifier
                break
            }
        }

        val currEffectLevel: Int

        player.removePotionEffect(PotionEffectType.WEAKNESS)

        currEffectLevel = when (healthAfterDamage) {
            in 0.0..5.0 -> {
                player.addPotionEffect(Effects.weakness3)
                player.inventory.helmet = Items.diamondHelmet
                player.inventory.chestplate = Items.diamondChestplate
                player.inventory.leggings = Items.diamondLeggings
                player.inventory.boots = Items.diamondBoots
                2
            }

            in 5.0 .. 10.0 -> {
                player.addPotionEffect(Effects.weakness2)
                player.inventory.helmet = Items.ironHelmet
                player.inventory.chestplate = Items.diamondChestplate
                player.inventory.leggings = Items.diamondLeggings
                player.inventory.boots = Items.ironBoots
                1
            }

            in 10.0 .. 15.0 -> {
                player.addPotionEffect(Effects.weakness1)
                player.inventory.helmet = Items.ironHelmet
                player.inventory.chestplate = Items.diamondChestplate
                player.inventory.leggings = Items.ironLeggings
                player.inventory.boots = Items.ironBoots
                0
            }

            in 15.0 .. 20.0 -> {
                player.inventory.helmet = Items.ironHelmet
                player.inventory.chestplate = Items.ironChestplate
                player.inventory.leggings = Items.ironLeggings
                player.inventory.boots = Items.ironBoots
                -1
            }

            else -> 0
        }

        if (currEffectLevel != prevEffectLevel) {
            CustomEffect.playParticle(player, Effect.HAPPY_VILLAGER, InGaussian(500, Vector(0.5f, 0.5f, 0.5f)))
        }

        LogWriter.log("${player.name} use 走為上策 health $healthAfterDamage")
    }
}