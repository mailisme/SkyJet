package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{

    PvpPlace Pvp = new PvpPlace();
    private PlayerInteractEvent event;

    List<World> PVPWorlds = new ArrayList<World>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("MAGIC PVP v.0.0.0");
        getServer().getPluginManager().registerEvents(this, this);
        World lobbyWorld = Bukkit.createWorld(new WorldCreator("Lobby"));

        for (int i = 0; i < 3; i++) {
            World world = Bukkit.createWorld(new WorldCreator(String.format("PVP%d", i)));
            world.setPVP(true);
            PVPWorlds.add(world);
        }

        getServer().getPluginManager().registerEvents(Pvp, this);
        if (lobbyWorld != null) {
            getLogger().info("Lobby world loaded successfully.");
        } else {
            getLogger().warning("Failed to load Lobby world.");
        }
        for (World world : Bukkit.getWorlds()) {
            getLogger().info("Loaded world: " + world.getName());
        }

        lobbyWorld.setPVP(false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
        if (command.getName().equalsIgnoreCase("lobby")){
            Player player = (Player) sender;
            for(int i = 0; i < 2; i++) {
                if (Pvp.players.get(i) == player) {
                    Pvp.players.remove(player);
                    player.teleport(lobby);
                }
                if (Pvp.players1.get(i) == player) {
                    Pvp.players1.remove(player);
                    player.teleport(lobby);
                }
                if (Pvp.players2.get(i) == player) {
                    Pvp.players2.remove(player);
                    player.teleport(lobby);
                }
            }
        }
        return true;
    }




    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
        event.setJoinMessage(ChatColor.AQUA + "Welcome "+ player.getName());
        player.getInventory().clear();
        player.teleport(lobby);
        player.getInventory().setItem(0, DiamondSword);
        System.out.print(player.getName()+" join the server");
    }

    @EventHandler
    public void Click(PlayerInteractEvent event){
        this.event = event;
        Player player = event.getPlayer();
        if(player.getWorld()==Bukkit.getWorld("Lobby")){
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
        else{
            return;
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

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        Player player = event.getEntity();
        player.getInventory().clear();
        player.getInventory().setItem(0, DiamondSword);
    }

    @EventHandler
    public void PlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        if (PVPWorlds.contains(event.getPlayer().getWorld())) {
            ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
            player.getInventory().setItem(0, IronSword);
            player.setGameMode(GameMode.ADVENTURE);
        }
        else if (event.getPlayer().getWorld() == Bukkit.getWorld("Lobby")) {
            ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
            player.getInventory().setItem(0, DiamondSword);
            player.setHealth(20);
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}