package me.minecraft.minecraftpvpplugin.pvp_place

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.effect.CustomEffect
import me.minecraft.minecraftpvpplugin.effect.InGaussian
import me.minecraft.minecraftpvpplugin.effect.InGaussianBounded
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.util.Vector

class Portal (private val center: Location, size: Vector, private val toLocation: Location) : Listener {
    private val boundSmaller: Location = center.clone().add(size.clone().multiply(-0.5))
    private val boundBigger: Location = center.clone().add(size.clone().multiply(0.5))

    init {
        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)

        RunEveryFor(3.0) {
            CustomEffect.playParticle(center, Effect.PORTAL, InGaussianBounded(3000, size))
        }
    }

    @EventHandler
    fun handlePlayerMove(event: PlayerMoveEvent) {
        if (event::class == PlayerTeleportEvent::class) return

        val player = event.player

        if (!inTrigger(event.from) && inTrigger(event.to)) {
            player.teleport(toLocation)
        }
    }

    private fun inTrigger(location: Location): Boolean {
        return boundSmaller.x < location.x && location.x < boundBigger.x &&
            boundSmaller.y < location.y && location.y < boundBigger.y &&
            boundSmaller.z < location.z && location.z < boundBigger.z
    }
}