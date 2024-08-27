package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.EffectShape
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.Skill
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

        if (healthAfterDamage > 15) {
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.ironChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            currEffectLevel = -1
        } else if (healthAfterDamage > 10) {
            player.addPotionEffect(Effects.weakness1)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            currEffectLevel = 0
        } else if (healthAfterDamage > 5) {
            player.addPotionEffect(Effects.weakness2)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.diamondLeggings
            player.inventory.boots = Items.ironBoots
            currEffectLevel = 1
        } else {
            player.addPotionEffect(Effects.weakness3)
            player.inventory.helmet = Items.diamondHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.diamondLeggings
            player.inventory.boots = Items.diamondBoots
            currEffectLevel = 2
        }

        if (currEffectLevel != prevEffectLevel) {
            CustomEffect.playParticle(player, Effect.HAPPY_VILLAGER, 300, EffectShape.InGaussian, 0.5f)
        }

        LogWriter.log("${player.name} use 走為上策 health $healthAfterDamage")
    }
}