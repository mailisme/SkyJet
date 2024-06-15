package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Invisible : Gadget(Material.STAINED_GLASS_PANE, "虛影斗篷", duration = 5.0) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.addPotionEffect(Effects.invisibleEffect)
        player.inventory.helmet = null
        player.inventory.chestplate = null
        player.inventory.leggings = null
        player.inventory.boots = null
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.removePotionEffect(Effects.invisibleEffect.type)
        player.inventory.helmet = Items.ironHelmet
        player.inventory.chestplate = Items.ironChestplate
        player.inventory.leggings = Items.ironLeggings
        player.inventory.boots = Items.ironBoots
    }
}
