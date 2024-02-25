package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

class KnockBack : Gadget(Material.FIREBALL, "地球之心", 20) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player as Player
        val nearBy = player.getNearbyEntities(6.0, 6.0, 6.0)

        for (e in nearBy) {
            println(e)
            val EntityPos = e.location.toVector()
            val PlayerPos = player.location.toVector()
            e.velocity = EntityPos.subtract(PlayerPos).setY(0).normalize().multiply(2).add(Vector(0.0, 0.7, 0.0))
        }
    }
}
