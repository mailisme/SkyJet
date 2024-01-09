package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.ArrayList;
import java.util.List;


public class PvpPlace implements Listener {
    List<Player> players = new ArrayList<Player>();

    @EventHandler
    public void ClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        World PVP1 = Bukkit.getWorld("PVP1");
        Location PVP1Location = new Location(PVP1, 110.5, 67, 95.5);
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Join Game")){
                switch (event.getCurrentItem().getType()){
                    case DIAMOND_AXE:
                        player
.closeInventory();
                        if (players.size() >= 2) {
                            player.sendMessage("Too many people");
                            System.out.println(players);
                        }
                        else {
                            players.add(player);
                            player.teleport(PVP1Location);
                            System.out.println(players);
                        }
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        event.getEntity().sendMessage(ChatColor.DARK_BLUE + "You Lose...");
        players.remove(event.getEntity());
        players.getFirst().sendMessage(ChatColor.GOLD + "You Won!");
        Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
        players.getFirst().teleport(lobby);
        players.removeFirst();

        World world = event.getEntity().getWorld();
        List<Entity> entList = world.getEntities();//get all entities in the world
    }
}
