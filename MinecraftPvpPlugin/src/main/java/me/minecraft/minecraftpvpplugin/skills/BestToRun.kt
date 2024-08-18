package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.ChatColor
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
        if (healthAfterDamage < 15 && healthAfterDamage > 10) {
            player.removePotionEffect(PotionEffectType.WEAKNESS)
            player.addPotionEffect(Effects.weakness1)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            LogWriter.LogWriter(player.name+" use 走為上策\n")

        } else if (healthAfterDamage < 10 && healthAfterDamage > 5) {
            player.removePotionEffect(PotionEffectType.WEAKNESS)
            player.addPotionEffect(Effects.weakness2)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.diamondLeggings
            player.inventory.boots = Items.ironBoots
            LogWriter.LogWriter(player.name+" use 走為上策\n")

        } else if (healthAfterDamage > 0 && healthAfterDamage < 5) {
            player.removePotionEffect(PotionEffectType.WEAKNESS)
            player.addPotionEffect(Effects.weakness3)
            player.inventory.helmet = Items.diamondHelmet
            player.inventory.chestplate = Items.diamondChestplate
            player.inventory.leggings = Items.diamondLeggings
            player.inventory.boots = Items.diamondBoots
            LogWriter.LogWriter(player.name+" use 走為上策\n")

        } else if (healthAfterDamage > 15) {
            player.removePotionEffect(PotionEffectType.WEAKNESS)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.ironChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            LogWriter.LogWriter(player.name+" use 走為上策\n")
        }
    }
}