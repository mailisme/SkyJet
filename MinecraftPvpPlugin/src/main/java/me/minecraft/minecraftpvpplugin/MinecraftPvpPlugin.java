package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{


    // 獲取Lobby世界
    World lobbyWorld = Bukkit.getWorld("Lobby");
    Location targetLocation = new Location(lobbyWorld, 100, 64, 100);



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

    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        event.setJoinMessage(ChatColor.AQUA + "Welcome "+ player.getName());
        player.getInventory().clear();
        player.getInventory().setItem(0, DiamondSword);
        World Lobby = Bukkit.getServer().getWorld("Lobby");
        if(Lobby == null) {
            System.out.print(Lobby);
        }
        else {
            System.out.print(Lobby);
        }
        System.out.print(player.getName()+" join the server");
    }

}
