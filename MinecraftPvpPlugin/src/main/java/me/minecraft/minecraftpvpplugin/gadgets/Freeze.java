package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Effect;
import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Freeze extends Gadget implements Listener {
    Map<Player, Location> FreezePoints = new HashMap<Player, Location>();
    List<Player> PlayersFreezing = new ArrayList<>();
    double distance = 3;

    public Freeze() {
        super(Material.SNOW_BALL, "冷陸氣團", 5);
    }

    @Override
    public void activate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        FreezePoints.put(player, player.getLocation());

        for (Location loc : FreezePoints.values()) {
            if (loc.distance(event.getFrom()) < 3 && !PlayersFreezing.contains(player)) {
                player.addPotionEffect(Effect.FreezeEffect);
                PlayersFreezing.add(player);
            }
        }
    }

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event) {
        for (Location loc : FreezePoints.values()) {
            Player player = event.getPlayer();

            if (loc.distance(event.getFrom()) < 3 && !PlayersFreezing.contains(player)) {
                player.addPotionEffect(Effect.FreezeEffect);
                PlayersFreezing.add(player);
            }
        }
    }
    @Override
        public void deactivate(PlayerInteractEvent event) {
            Player player = event.getPlayer();

            for (Player freezing : PlayersFreezing) {
                freezing.removePotionEffect(Effect.FreezeEffect.getType());
            }
            FreezePoints.remove(player);
            PlayersFreezing.remove(player);
    }
}
