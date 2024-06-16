package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector
import kotlin.random.Random

object TeleportToOpponent : Skill(Material.REDSTONE_BLOCK, "閃電突襲", coolDownSeconds = 10.0, ignoreCoolDownSeconds = 1.0){
    @EventHandler
    public fun handleClick(event: PlayerInteractEvent) {
        val player = event.player

        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        val opponentLocation = PvpPlaceManager.getOpponent(player)!!.location

        val targetLocation = opponentLocation
            .subtract(
            player.location.direction
                .multiply(Vector(1, 0, 1))
                .normalize()
                .multiply(2)
        )

        targetLocation.setDirection(
            opponentLocation
                .toVector()
                .subtract(player.location.toVector())
                .normalize()
        )

        player.teleport(targetLocation)
    }
}