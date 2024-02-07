package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class KnockBack extends Gadget {




    public KnockBack() {
        super(Material.FIREBALL, "地球之心", 20);
    }

    @Override
    protected void onActivate(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();
        List<Entity> nearBy = player.getNearbyEntities(6, 6, 6);

        for (Entity e : nearBy) {
            System.out.println(e);
            Vector EntityPos = e.getLocation().toVector();
            Vector PlayerPos = player.getLocation().toVector();
            e.setVelocity(EntityPos.subtract(PlayerPos).setY(0).normalize().multiply(2).add(new Vector(0, 0.7, 0)));
        }
    }

    @Override
    protected void onDeactivate(PlayerInteractEvent event) {

    }
}
