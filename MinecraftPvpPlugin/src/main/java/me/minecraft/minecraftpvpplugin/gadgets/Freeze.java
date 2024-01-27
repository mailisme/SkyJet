package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.ThrowableGadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class Freeze extends ThrowableGadget {
    public Freeze() {
        super(Material.SNOW_BALL, "冷陸氣團");
    }

    @Override
    public void onThrow(ProjectileLaunchEvent event){
        Player player = (Player) event.getEntity().getShooter();
        player.sendMessage("throw");
    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();
        player.sendMessage("get hit");
    }

    @Override
    public void onHitObject(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        player.sendMessage("you hit something");
    }
}
