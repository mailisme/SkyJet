package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Invisible : Gadget(Material.STAINED_GLASS_PANE, "虛影斗篷", lore = listOf(
    "${ChatColor.YELLOW}賦予隱形效果",
    "${ChatColor.GRAY}持續時間：5秒"
), duration = 5.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 虛影斗篷")
        player.addPotionEffect(Effects.invisible)
        player.inventory.helmet = null
        player.inventory.chestplate = null
        player.inventory.leggings = null
        player.inventory.boots = null
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 虛影斗篷 disabled")
        player.removePotionEffect(Effects.invisible.type)
        player.inventory.helmet = Items.ironHelmet
        player.inventory.chestplate = Items.ironChestplate
        player.inventory.leggings = Items.ironLeggings
        player.inventory.boots = Items.ironBoots
    }
}
