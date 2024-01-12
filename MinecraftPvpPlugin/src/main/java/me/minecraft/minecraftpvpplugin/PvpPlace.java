package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Entity;
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


                        if (this.players.size() >= 2) {
                            if(players1.size() >= 2){
                                if(players2.size() >= 2){
                                    player.sendMessage("server is full");
                                }else if(players2.size() == 1){
                                    player.teleport(new Location(PVP3, 118.5, 98, 54.5));
                                    players2.add(player);
                                }
                                else{
                                    player.teleport(new Location(PVP3, 188.5, 98, 84.5));
                                    players2.add(player);
                                }
                            } else if(players1.size() == 1){
                                player.teleport(new Location(PVP2, 118.5, 98, 54.5));
                                players1.add(player);
                            }else {
                                player.teleport(new Location(PVP2, 118.5, 98, 84.5));
                                players1.add(player);
                            }
                        }
                        else if(players.size() == 1){
                            player.teleport(new Location(PVP1, 118.5, 98, 54.5));
                            this.players.add(player);
                        }else {
                            player.teleport(new Location(PVP2, 118.5, 98, 84.5));
                            players.add(player);
                        }
                        break;
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        if(event.getEntity().getWorld()==Bukkit.getWorld("PVP1")) {
            event.getEntity().sendMessage(ChatColor.DARK_BLUE + "You Lose...");
            players.remove(event.getEntity());
            players.getFirst().sendMessage(ChatColor.GOLD + "You Won!");
            Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
            players.getFirst().teleport(lobby);
            players.removeFirst();
        } else if (event.getEntity().getWorld()==Bukkit.getWorld("PVP2")) {
            event.getEntity().sendMessage(ChatColor.DARK_BLUE + "You Lose...");
            players1.remove(event.getEntity());
            players1.getFirst().sendMessage(ChatColor.GOLD + "You Won!");
            Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
            players1.getFirst().teleport(lobby);
            players1.removeFirst();
        } else if (event.getEntity().getWorld()==Bukkit.getWorld("PVP3")) {
            event.getEntity().sendMessage(ChatColor.DARK_BLUE + "You Lose...");
            players2.remove(event.getEntity());
            players2.getFirst().sendMessage(ChatColor.GOLD + "You Won!");
            Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
            players2.getFirst().teleport(lobby);
            players2.removeFirst();
        }
        World world = event.getEntity().getWorld();
        List<Entity> entList = world.getEntities();//get all entities in the world
    }
    @EventHandler
    public void PlayerLeaveInPVP(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(player.getWorld()==Bukkit.getWorld("PVP1")){
            players.remove(player);
            Player LeftPlayer = (Player) players.get(0);
            players.clear();
        }
    }
}
