package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Effect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.Items
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Invisible : Gadget(Material.STAINED_GLASS_PANE, "虛影斗篷", duration = 5) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.addPotionEffect(Effect.invisibleEffect)
        player.inventory.helmet = null
        player.inventory.chestplate = null
        player.inventory.leggings = null
        player.inventory.boots = null
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.inventory.helmet = Items.ironHelmet
        player.inventory.chestplate = Items.ironChestplate
        player.inventory.leggings = Items.ironLeggings
        player.inventory.boots = Items.ironBoots
    }
}
