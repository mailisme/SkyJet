package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object Rebound : Gadget(Material.WOOD_DOOR, "反射之盾", duration = 10.0), Listener {
    @EventHandler
    fun entityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player
            LogWriter.LogWriter(player.name+" use 反射之盾\n")

            if (isActivating(event.entity as Player)) {
                val health = player.health.toInt()
                val hurt = event.damage.toInt()
                player.health = health - hurt.toDouble() / 2
            }
        }
    }
}
