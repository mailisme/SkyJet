package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Effect;
import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin;
import me.minecraft.minecraftpvpplugin.PvpPlace;
import me.minecraft.minecraftpvpplugin.ThrowableGadget;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Freeze extends ThrowableGadget{
    List<Player> FrozePlayer = new ArrayList<>();

    public Freeze() throws Exception {
        super(Material.SNOW_BALL, "冷陸氣團");
    }

    @Override
    public void onThrow(ProjectileLaunchEvent event){

    }

    @Override
    public void onHitEntity(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();
        player.sendMessage("u get hit");
        player.addPotionEffect(Effect.FreezeEffect);
        FrozePlayer.add(player);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        FrozePlayer.remove(player);
                    }
                },
                5000
        );
    }

    @EventHandler
        public void Move(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            if (MinecraftPvpPlugin.IsInPvp(player) && FrozePlayer.contains(player)) {
                event.getPlayer().teleport(event.getFrom());
            }
        }

        @Override
        public void onHitObject(ProjectileHitEvent event) {
            Player player = (Player) event.getEntity().getShooter();
            Map<Location, Material> locationMaterialMap = new HashMap<>();
            Location IceCenter = event.getEntity().getLocation();
            Vector EntityVelocity = event.getEntity().getVelocity();
            IceCenter.add(EntityVelocity.normalize());
            Block block =  IceCenter.getBlock();
            Map<Integer, Integer> xy;
            for (int i = -3; i <= 3; i++) {
                for (int j = -3; j <= 3; j++) {
                    float BlockX = block.getX()+i;
                    float BlockZ = block.getZ()+j;
                }
            }
            locationMaterialMap.put(IceCenter, block.getType());

            block.setType(Material.ICE);
    }
}
