package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.refs.Effects
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Speed : Gadget(Material.LEATHER_BOOTS, "風行之靴", lore = listOf(
    "${ChatColor.YELLOW}賦予${ChatColor.AQUA}加速效果Ⅵ",
    "${ChatColor.GRAY}持續時間：10秒"
), duration = 10.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 風行之靴")
        player.addPotionEffect(Effects.speed)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 風行之靴 disabled")
        player.removePotionEffect(Effects.speed.type)
    }
}
