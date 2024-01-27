package me.minecraft.minecraftpvpplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static org.bukkit.Bukkit.getBukkitVersion;
import static org.bukkit.Bukkit.getServer;

abstract public class ThrowableGadget extends ItemStack implements Listener {
    public Material material;
    public String name;

    Class<?> ProjectileType;

    protected abstract void onThrow(ProjectileLaunchEvent event);
    protected abstract void onHitEntity(EntityDamageByEntityEvent event);
    protected abstract void onHitObject(ProjectileHitEvent event);


    public ThrowableGadget(Material material, String name) throws Exception {
        this.material = material;
        this.name = name;

        switch (material) {
            case Material.SNOW_BALL -> ProjectileType = Snowball.class;
            case Material.EGG -> ProjectileType = Egg.class;
            case Material.EXP_BOTTLE -> ProjectileType = ThrownExpBottle.class;
            case Material.ARROW -> ProjectileType = Arrow.class;
            case Material.FISHING_ROD -> ProjectileType = FishHook.class;
            case Material.FIREBALL -> ProjectileType = Fireball.class;
            case Material.POTION -> ProjectileType = ThrownPotion.class;
            case Material.ENDER_PEARL -> ProjectileType = EnderPearl.class;
            default -> throw new Exception("no this item");
        }

        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, getServer().getPluginManager().getPlugin("MinecraftPvpPlugin"));
    }

    @EventHandler
    public void Throw(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            System.out.println(ProjectileType);
            System.out.println(event.getEntity().getClass());
            if (event.getEntity().getClass().equals(ProjectileType)) {
                System.out.println("Class yes");
                onThrow(event);
            }
        }
    }

    @EventHandler
    public void HitEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof ItemStack item) {
            if (Objects.equals(item.getItemMeta().getDisplayName(), name)) {
                onHitEntity(event);
            }
        }
    }
    @EventHandler
    public void HitObject(ProjectileHitEvent event) {
        ItemStack item = (ItemStack) event.getEntity();
        if (Objects.equals(item.getItemMeta().getDisplayName(), name)) {
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
