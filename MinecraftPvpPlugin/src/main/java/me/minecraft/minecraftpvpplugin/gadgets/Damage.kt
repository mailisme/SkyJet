package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.refs.Effects
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Damage : Gadget(Material.LAPIS_ORE, "劍魂之石", lore = listOf(
    "${ChatColor.YELLOW}賦予${ChatColor.AQUA}增加攻擊力Ⅰ",
    "${ChatColor.GRAY}持續時間：10秒"
), duration = 10.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 劍魂之石")
        player.addPotionEffect(Effects.damage)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 劍魂之石 disabled")
        player.removePotionEffect(Effects.damage.type)
    }
}
