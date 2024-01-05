package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{


    private PlayerInteractEvent event;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MAGIC PVP v.0.0.0");
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(this, this);
        World lobbyWorld = Bukkit.createWorld(new WorldCreator("Lobby"));
        World PVPWorld1 = Bukkit.createWorld(new WorldCreator("PVP1"));

        if (lobbyWorld != null) {
            getLogger().info("Lobby world loaded successfully.");
        } else {
            getLogger().warning("Failed to load Lobby world.");
        }
        for (World world : Bukkit.getWorlds()) {
            getLogger().info("Loaded world: " + world.getName());
        }

        getServer().getPluginManager().registerEvents(new GuiEvent(), this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
        if (command.getName().equalsIgnoreCase("lobby")){
            Player player = (Player) sender;
            player.teleport(lobby);
        }
        return true;
    }


    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
        ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        event.setJoinMessage(ChatColor.AQUA + "Welcome "+ player.getName());
        player.getInventory().clear();
        player.getInventory().setItem(0, DiamondSword);
        System.out.print(player.getName()+" join the server");
        player.teleport(lobby);
    }

    @EventHandler

    public void Click(PlayerInteractEvent event){
        this.event = event;
        Player player = event.getPlayer();
        Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA+"Join Game");
        ItemStack StartGame = new ItemStack(Material.DIAMOND_AXE);
        ItemStack[] menu = {StartGame};
        gui.setContents(menu);

        if (event.getItem() != null) {
            if (event.getAction()==Action.LEFT_CLICK_AIR && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))){
                player.openInventory(gui);
            } else if (event.getAction()==Action.LEFT_CLICK_BLOCK && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
                player.openInventory(gui);
            } else if (event.getAction()==Action.RIGHT_CLICK_AIR && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
                player.openInventory(gui);
            } else if (event.getAction()==Action.RIGHT_CLICK_BLOCK && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
                player.openInventory(gui);
            }
        }
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(Bukkit.getWorld("Lobby")==player.getWorld()){
            player.sendMessage("U cant drop any item ok?");
            event.getItemDrop().remove();
            player.getInventory().setItem(0, event.getItemDrop().getItemStack());
        }

    }

}

