package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class MinecraftPvpPlugin extends JavaPlugin implements Listener{

    static List<World> PVPWorlds = new ArrayList<World>();

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

    @EventHandler
    public void ClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Join Game")){
                switch (event.getCurrentItem().getType()){
                    case DIAMOND_AXE:
                        PvpPlace.AddPlayer(player);
                        player.closeInventory();
                        break;
                }
                event.setCancelled(true);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("lobby")) {
            Player player = (Player) sender;
            PvpPlace.RemovePlayer(player);
            ToLobby(player);
        }

        return true;
    }

    @EventHandler
    public void Join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.AQUA + "Welcome " + player.getName());
        System.out.print(player.getName() + " join the server");
        player.teleport(Locations.lobby);
        ToLobby(player);
    }

    @EventHandler
    public void Click(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getWorld()==Bukkit.getWorld("Lobby")){
            Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA+"Join Game");
            ItemStack StartGame = Item.DiamondPickaxe;
            ItemStack[] menu = {StartGame};
            gui.setContents(menu);

            if (event.getItem() != null) {
                if (event.getItem().equals(Item.DiamondSword)) {
                    if (event.getAction() != Action.PHYSICAL) {
                        player.openInventory(gui);
                    }
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
        Player player = event.getEntity();
        player.spigot().respawn();
        PvpPlace.RemovePlayer(player);
        event.setKeepInventory(true);
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (PVPWorlds.contains(player.getWorld())) {
            PvpPlace.RemovePlayer(player);
        }
    }

    static void ToLobby(Player player) {
        System.out.println("To Lobby " + player.getName());
        player.getInventory().clear();
        Item.SwordItemMeta.setDisplayName(ChatColor.AQUA + "Join");
        Item.SwordItemMeta.spigot().setUnbreakable(true);
        Item.DiamondSword.setItemMeta(Item.SwordItemMeta);
        player.getInventory().setItem(0, Item.DiamondSword);
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
    }

    static void ToPVP(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, Item.IronSword);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().setHelmet(Item.IronHelmet);
        player.getInventory().setChestplate(Item.IronChestplate);
        player.getInventory().setLeggings(Item.IronLeggings);
        player.getInventory().setBoots(Item.IronBoots);
        player.setGameMode(GameMode.ADVENTURE);
    }
}