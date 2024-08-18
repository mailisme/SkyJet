package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Anchor : Gadget(Material.ANVIL, "時空之錨", lore = listOf(
    "${ChatColor.YELLOW}使用時記住位置，再使用傳回",
    "${ChatColor.GRAY}傳送僅能使用一次"
), switchLike = true) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 時空之錨 at ${player.location}")
        addPlayerData(player, "anviledLocation", player.location)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        val targetLocation = getPlayerData(player, "anviledLocation") as Location?
        LogWriter.log("${player.name} use 時空之錨 from ${player.location} to $targetLocation \n")
        player.teleport(targetLocation)
    }
}
