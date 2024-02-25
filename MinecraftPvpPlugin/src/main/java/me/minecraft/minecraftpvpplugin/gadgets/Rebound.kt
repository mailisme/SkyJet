package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class Rebound : Gadget(Material.WOOD_DOOR, "反射之盾", 10), Listener {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.sendMessage("start")
    }

    @EventHandler
    fun EntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player

            if (PlayersUsingGadget.contains(event.entity as Player)) {
                val health = player.health.toInt()
                val hurt = event.damage.toInt()
                player.health = health - hurt.toDouble() / 2
            }
        }
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.sendMessage("stop")
    }
}
