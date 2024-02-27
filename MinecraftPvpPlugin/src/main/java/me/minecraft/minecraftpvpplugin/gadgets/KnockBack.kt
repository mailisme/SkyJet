package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

object KnockBack : Gadget(Material.FIREBALL, "地球之心", duration = 20) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player as Player
        val nearBy = player.getNearbyEntities(6.0, 6.0, 6.0)

        nearBy.forEach { entity ->
            val entityPos = entity.location.toVector()
            val playerPos = player.location.toVector()
            entity.velocity = entityPos
                    .subtract(playerPos)
                    .setY(0)
                    .normalize()
                    .multiply(2)
                    .add(Vector(0.0, 0.7, 0.0))
        }
    }
}
