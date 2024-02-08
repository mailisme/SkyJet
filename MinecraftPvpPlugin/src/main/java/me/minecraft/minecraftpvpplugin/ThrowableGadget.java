package me.minecraft.minecraftpvpplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static org.bukkit.Bukkit.getBukkitVersion;
import static org.bukkit.Bukkit.getServer;

abstract public class ThrowableGadget extends ItemStack implements Listener {
    public Material material;
    public String name;

    EntityType ProjectileType;

    protected void onThrow(ProjectileLaunchEvent event) {

    }
    protected void onHitEntity(EntityDamageByEntityEvent event) {

    }
    protected void onHitObject(ProjectileHitEvent event) {

    }


    public ThrowableGadget(Material material, String name) throws Exception {
        this.material = material;
        this.name = name;

        switch (material) {
            case Material.SNOW_BALL -> ProjectileType = EntityType.SNOWBALL;
            case Material.EGG -> ProjectileType = EntityType.EGG;
            case Material.EXP_BOTTLE -> ProjectileType = EntityType.THROWN_EXP_BOTTLE;
            case Material.ARROW -> ProjectileType = EntityType.ARROW;
            case Material.FISHING_ROD -> ProjectileType = EntityType.FISHING_HOOK;
            case Material.FIREBALL -> ProjectileType = EntityType.FIREBALL;
            case Material.POTION -> ProjectileType = EntityType.SPLASH_POTION;
            case Material.ENDER_PEARL -> ProjectileType = EntityType.ENDER_PEARL;
            default -> throw new Exception("no this item");
        }

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance);
    }

    @EventHandler
    public void Throw(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity().getType().equals(ProjectileType)) {
                onThrow(event);
            }
        }
    }

    @EventHandler
    public void HitEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Entity) {
            if (event.getDamager().getType() == ProjectileType) {
                onHitEntity(event);
            }
        }
    }

    @EventHandler
    public void HitObject(ProjectileHitEvent event) {
        Entity damager = event.getEntity();
        if (damager.getType() == ProjectileType) {
            onHitObject(event);
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
