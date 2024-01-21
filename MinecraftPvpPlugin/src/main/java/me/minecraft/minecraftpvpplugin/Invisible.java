package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Invisible extends Gadget{

    public Invisible() {
        super(Material.STAINED_GLASS_PANE, "虛影斗篷", 5);
    }

    public void activate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.InvisibleEffect);
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void deactivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setHelmet(Items.IronHelmet);
        player.getInventory().setChestplate(Items.IronChestplate);
        player.getInventory().setLeggings(Items.IronLeggings);
        player.getInventory().setBoots(Items.IronBoots);
    }
}
