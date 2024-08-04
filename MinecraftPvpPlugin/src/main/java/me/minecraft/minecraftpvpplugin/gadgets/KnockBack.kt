package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

object KnockBack : Gadget(material = Material.FIREBALL, "地球之心", lore = listOf(
    "${ChatColor.YELLOW}震走半徑6格內的實體",
), duration = 0.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player as Player
        val nearByEntities = player.getNearbyEntities(6.0, 6.0, 6.0)

        nearByEntities.forEach { entity ->
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
