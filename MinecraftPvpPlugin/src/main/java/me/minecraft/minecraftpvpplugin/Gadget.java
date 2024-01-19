package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

abstract public class Gadget extends ItemStack implements Listener {
    Material material;
    public String name;
    public long duration;

    public String uuid;

    abstract void activate(PlayerInteractEvent event);
    abstract void deactivate(PlayerInteractEvent event);

    public Gadget(Material material, String name, long duration, int amount) {
        this.material = material;
        this.name = name;
        this.duration = duration;

        this.setAmount(amount);

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<String>();
        uuid = UUID.randomUUID().toString();
        lore.add(uuid);
        meta.setLore(lore);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, getServer().getPluginManager().getPlugin("MinecraftPvpPlugin"));
    }

    @EventHandler
    public void Click(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(MinecraftPvpPlugin.PVPWorlds.contains(player.getWorld())){
            event.getPlayer().sendMessage("YEE");
            if (event.getItem() != null) {
                if (Objects.equals(event.getItem().getItemMeta().getLore().getFirst(), uuid)) {
                    if (event.getAction() != Action.PHYSICAL) {
                        activate(event);
                        this.setDurability((short) 0);
                        try {
                            TimeUnit.SECONDS.sleep(duration);
                        } catch (InterruptedException _){}
                        deactivate(event);
                    }
                }
            }
        }
    }
}
