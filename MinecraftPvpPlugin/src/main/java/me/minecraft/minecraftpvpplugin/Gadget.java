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
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getServer;

abstract public class Gadget extends ItemStack implements Listener {
    public Material material;
    public String name;
    public long duration;

    public List<Player> PlayersUsingGadget = new ArrayList<>();

    protected abstract void activate(PlayerInteractEvent event);
    protected abstract void deactivate(PlayerInteractEvent event);


    public Gadget(Material material, String name, long duration) {
        this.material = material;
        this.name = name;
        this.duration = duration;

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

            if (item.getAmount() == 1) {
                player.getInventory().setItemInHand(null);
            }
            else {
                player.getInventory().setItemInHand(this.instance(item.getAmount() - 1));
            }

            PlayersUsingGadget.add(player);
            activate(event);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            PlayersUsingGadget.remove(player);
                            deactivate(event);
                        }
                    },
                    duration * 1000
            );
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
