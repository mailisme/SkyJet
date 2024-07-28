package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Anchor : Gadget(Material.ANVIL, "時空之錨", switchLike = true) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.LogWriter(player.name+" use 時空之錨\n")
        addPlayerData(player, "anviledLocation", player.location)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.teleport(getPlayerData(player, "anviledLocation") as Location?)
    }
}
