package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

abstract public class Gadget extends ItemStack implements Listener {
    public Material material;
    public String name;
    public long duration; // this will only do any effect when SwitchLike = false

    boolean activating;

    // Switch Like:
    //     false: player click -> activate() -> delete item -> wait for `duration` s  -> deactivate()
    //     true: player click -> activate() -> player click -> deactivate() -> delete item

    public boolean SwitchLike;

    public List<Player> PlayersUsingGadget = new ArrayList<>();

    protected abstract void onActivate(PlayerInteractEvent event);
    protected abstract void onDeactivate(PlayerInteractEvent event);


    public Gadget(Material material, String name, long duration, boolean SwitchLike) {
        this.material = material;
        this.name = name;
        this.duration = duration;
        this.SwitchLike = SwitchLike;

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, getServer().getPluginManager().getPlugin("MinecraftPvpPlugin"));
    }

    public Gadget(Material material, String name, long duration) {
        this.material = material;
        this.name = name;
        this.duration = duration;
        this.SwitchLike = false;

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, getServer().getPluginManager().getPlugin("MinecraftPvpPlugin"));
    }

@EventHandler
    public void Click(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getItem() != null
                && MinecraftPvpPlugin.PVPWorlds.contains(player.getWorld())
                && Objects.equals(item.getItemMeta().getDisplayName(), name)
                && event.getAction() != Action.PHYSICAL
                && !PlayersUsingGadget.contains(player)
        ) {
            if (SwitchLike && activating) {
                RemoveOneItemInHand(player);
                PlayersUsingGadget.remove(player);
                onDeactivate(event);
                activating = false;
            }

            else {
                PlayersUsingGadget.add(player);
                onActivate(event);
                activating = true;
            }

            if (!SwitchLike) {
                RemoveOneItemInHand(player);

                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            PlayersUsingGadget.remove(player);
                            onDeactivate(event);
                            activating = false;
                        }
                    },
                    duration * 1000
                );
            }
        }
    }

    void RemoveOneItemInHand(Player player) {
        ItemStack item = player.getItemInHand();

        if (item.getAmount() == 1) {
            player.getInventory().setItemInHand(null);
        }
        else {
            player.getInventory().setItemInHand(instance(item.getAmount() - 1));
        }
    }

    public ItemStack instance(int amount) {
        ItemStack stack = this.clone();
        stack.setAmount(amount);
        return stack;
    }

    public ItemStack instance() {
        ItemStack stack = this.clone();
        stack.setAmount(1);
        return stack;
    }
}
