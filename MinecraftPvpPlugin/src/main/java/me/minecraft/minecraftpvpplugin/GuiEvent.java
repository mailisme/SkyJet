package me.minecraft.minecraftpvpplugin;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiEvent implements Listener {


    @EventHandler
    public void ClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        World PVP1 = Bukkit.getWorld("PVP1");
        Location PVP1Location = new Location(PVP1, 110.5, 67, 95.5);
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Join Game")){
            switch (event.getCurrentItem().getType()){
                case DIAMOND_AXE:
                    player.closeInventory();
                    player.teleport(PVP1Location);
            }
            event.setCancelled(true);
        }
    }
}
