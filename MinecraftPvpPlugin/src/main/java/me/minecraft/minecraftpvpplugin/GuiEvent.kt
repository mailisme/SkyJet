package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GuiEvent : Listener {
    @EventHandler
    fun ClickEvent(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val PVP1 = Bukkit.getWorld("PVP1")
        val PVP1Location = Location(PVP1, 110.5, 67.0, 95.5)
        if (event.clickedInventory != null) {
            if (event.clickedInventory.title.equals(ChatColor.AQUA.toString() + "Join Game", ignoreCase = true)) {
                when (event.currentItem.type) {
                    Material.DIAMOND_AXE -> {
                        player.closeInventory()
                        player.teleport(PVP1Location)
                    }

                    else -> {}
                }
                event.isCancelled = true
            }
        }
    }
}
