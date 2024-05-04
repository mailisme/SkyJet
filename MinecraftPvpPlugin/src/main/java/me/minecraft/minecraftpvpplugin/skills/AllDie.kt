package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object AllDie : Skill(Material.BONE, "同歸於盡"){
    @EventHandler
    public fun onDamage(event: EntityDamageEvent) {
        val player: Player = event.entity as Player
        if (player.health < 15 && player.health > 10) {
            player.addPotionEffect(Effects.AllDie1)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = null
            player.inventory.leggings = null
            player.inventory.boots = Items.ironBoots
        }
        else if (player.health < 10 && player.health > 5) {
            player.addPotionEffect(Effects.AllDie2)
            player.inventory.helmet = Items.leatherHelmet
            player.inventory.chestplate = Items.leatherChestplate
            player.inventory.leggings = Items.leatherLeggings
            player.inventory.boots = Items.leatherBoots
        }
        else if (player.health > 0 && player.health < 5) {
            player.addPotionEffect(Effects.AllDie3)
            player.inventory.helmet = Items.leatherHelmet
            player.inventory.chestplate = null
            player.inventory.leggings = null
            player.inventory.boots = Items.leatherBoots
        }
        else if (player.health > 15) {
            player.removePotionEffect(Effects.AllDie1.type)
            player.removePotionEffect(Effects.AllDie2.type)
            player.removePotionEffect(Effects.AllDie3.type)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.ironChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
        }
    }
}