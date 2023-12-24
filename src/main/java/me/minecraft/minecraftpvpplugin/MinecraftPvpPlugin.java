package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MAGIC PVP v.0.0.0");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        event.setJoinMessage(ChatColor.AQUA + "Welcome "+ player.getName());
        player.getInventory().clear();
        player.getInventory().setItem(0, DiamondSword);
        World Lobby = Bukkit.getWorld("Lobby");
        if(Lobby == null) {
            System.out.print("World " + " is invalid!");
            return;
        }
        Location loc = new Location(Lobby, 100, 100, 100);

    }

}
