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
        float x = player.getLocation().getDirection().normalize().getBlockX();
        float y = player.getLocation().getDirection().normalize().getBlockY();
        List<Entity> nearBy = player.getNearbyEntities(6, 6, 6);
        Player Enemy =  (Player) nearBy.get(0);
        Enemy.setVelocity(getVelocity(x, y, 3));
    }
    private Vector getVelocity(double x, double z, double speed) {
        double y = 0.3333;
        double multiplier = Math.sqrt((speed*speed) / (x*x + y*y + z*z));
        return new Vector(x, y, z).multiply(multiplier).setY(y);
    }

    @Override
    protected void onDeactivate(PlayerInteractEvent event) {

    }
}
