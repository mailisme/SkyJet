package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.ThrowableGadget;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class Freeze extends ThrowableGadget implements Listener {
    double distance = 3;

    public Freeze() {
        super(Material.SNOW_BALL, "冷陸氣團");
    }

    @Override
    public void onThrow(ProjectileLaunchEvent event){
        System.out.println("Throw");
    }


    @Override
    public void onHit(ProjectileHitEvent event){
        System.out.println("Hit");
    }
}
