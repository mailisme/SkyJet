package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent

class Anchor : Gadget(Material.ANVIL, "時空之錨", true) {
    var AnviledLocation: MutableMap<Player, Location> = HashMap()

    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        AnviledLocation[player] = player.location
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player

        player.teleport(AnviledLocation[player])
        AnviledLocation.remove(player)
    }

    public override fun onGameEnd(event: PlayerChangedWorldEvent) {
        val player = event.player

        AnviledLocation.remove(player)
    }
}
