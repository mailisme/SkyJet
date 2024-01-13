package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;


public class PvpPlace implements Listener {
    List<List<Player>> players = new ArrayList<>();

    @EventHandler
    public void ClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Join Game")){
                switch (event.getCurrentItem().getType()){
                    case DIAMOND_AXE:
                        player.closeInventory();
                        break;
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {

    }
    @EventHandler
    public void PlayerLeaveInPVP(PlayerQuitEvent event){

    }
}
