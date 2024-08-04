package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object Rebound : Gadget(Material.WOOD_DOOR, "反射之盾", duration = 10.0, lore = listOf(
    "${ChatColor.YELLOW}反彈所受傷害的50%",
    "${ChatColor.GRAY}持續時間：10秒"
)), Listener {
    @EventHandler
    fun entityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player

            if (isActivating(event.entity as Player)) {
                val health = player.health.toInt()
                val hurt = event.finalDamage.toInt()
                player.health = health - hurt.toDouble() / 2
            }
        }
    }
}
