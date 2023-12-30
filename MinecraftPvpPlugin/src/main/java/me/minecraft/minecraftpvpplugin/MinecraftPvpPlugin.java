package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.event.ActionListener;

public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{


    private PlayerInteractEvent event;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MAGIC PVP v.0.0.0");
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(this, this);
        World lobbyWorld = Bukkit.createWorld(new WorldCreator("Lobby"));

        if (lobbyWorld != null) {
            getLogger().info("Lobby world loaded successfully.");
        } else {
            getLogger().warning("Failed to load Lobby world.");
        }
        for (World world : Bukkit.getWorlds()) {
            getLogger().info("Loaded world: " + world.getName());
        }

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
        if (event.getAction()==Action.LEFT_CLICK_AIR && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))){
            System.out.print("Click!");
        } else if (event.getAction()==Action.LEFT_CLICK_BLOCK && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
            System.out.print("Click!");
        } else if (event.getAction()==Action.RIGHT_CLICK_AIR && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
            System.out.print("Click!");
        } else if (event.getAction()==Action.RIGHT_CLICK_BLOCK && event.getItem().equals(new ItemStack(Material.DIAMOND_SWORD))) {
            System.out.print("Click!");
        }
    }

}

