package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

abstract public class ThrowableGadget extends ItemStack implements Listener {
    public Material material;
    public String name;

    protected abstract void onThrow(ProjectileLaunchEvent event);
    protected abstract void onHit(PlayerInteractEvent event);

    public ThrowableGadget(Material material, String name) {
        if (
                material != Material.FISHING_ROD
                && material != Material.EXP_BOTTLE
                && material != Material.POTION
        )
        this.material = material;
        this.name = name;

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, getServer().getPluginManager().getPlugin("MinecraftPvpPlugin"));
    }

    @EventHandler
    void Throw(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Player)  {

        }
        onThrow(event);
    }

    @EventHandler
    void Hit(ProjectileHitEvent event) {
        onHit(event);
    }
}
