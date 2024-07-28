package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.refs.Effects
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Damage : Gadget(Material.LAPIS_ORE, "劍魂之石", duration = 10.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.LogWriter(player.name+" use 劍魂之石\n")
        player.addPotionEffect(Effects.damageEffect)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.removePotionEffect(Effects.damageEffect.type)
    }
}
