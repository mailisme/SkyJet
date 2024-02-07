package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.*;
import me.minecraft.minecraftpvpplugin.Effect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.*;

public class Freeze extends ThrowableGadget {

    public Freeze() throws Exception {
        super(Material.SNOW_BALL, "冷陸氣團");
    }

    @Override
    public void onThrow(ProjectileLaunchEvent event){

    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event){

    }

    @Override
    public void onHitObject(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Map<Location, MatWithData> locationMaterialMap = new HashMap<>();

        Location IceCenter = event.getEntity().getLocation();
        Vector EntityVelocity = event.getEntity().getVelocity();
        IceCenter.add(EntityVelocity.normalize());
        Block CenterBlock =  IceCenter.getBlock();

        int BlockY = CenterBlock.getY();

        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if (new Vector(i, j, 0).length() < 2.7) {
                    int BlockX = CenterBlock.getX()+i;
                    int BlockZ = CenterBlock.getZ()+j;

                    Block block = IceCenter.getWorld().getBlockAt(BlockX, BlockY, BlockZ);
                    if (!(block.getType() == Material.ICE)) {
                        locationMaterialMap.put(block.getLocation(), new MatWithData(block.getType(), block.getData()));
                    }

                    block.setType(Material.ICE);
                }
            }
        }

        Bukkit.getScheduler().runTaskLater(MinecraftPvpPlugin.instance, () -> {
            if (!locationMaterialMap.isEmpty()) {
                locationMaterialMap.forEach((location, matWithData) -> {
                    IceCenter.getWorld().getBlockAt(location).setType(matWithData.material);
                    IceCenter.getWorld().getBlockAt(location).setData(matWithData.data);
                    locationMaterialMap.remove(location, matWithData);
                });
            }
        }, 40);
    }
}
