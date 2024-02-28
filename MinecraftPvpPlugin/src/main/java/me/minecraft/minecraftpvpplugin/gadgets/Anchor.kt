package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Anchor : Gadget(Material.ANVIL, "時空之錨", switchLike = true) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        addPlayerData(player, "anviledLocation", player.location)
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.teleport(getPlayerData(player, "anviledLocation") as Location?)
    }
}
