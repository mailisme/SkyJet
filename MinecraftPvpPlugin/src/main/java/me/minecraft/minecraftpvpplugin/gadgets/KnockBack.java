package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class KnockBack extends Gadget {

    private Vector getVelocity(double x, double z, double speed) {
        double y = 0.3333; // this way, like normal knockback, it hits a player a little bit up
        double multiplier = Math.sqrt((speed*speed) / (x*x + y*y + z*z)); // get a constant that, when multiplied by the vector, results in the speed we want
        return new Vector(x, y, z).multiply(multiplier).setY(y);
    }
    public KnockBack() {
        super(Material.FIREBALL, "地球之心", 20);
    }

    @Override
    protected void onActivate(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();
        List<Entity> nearBy = player.getNearbyEntities(6, 6, 6);
        Player Enemy =  (Player) nearBy.get(0);
        Enemy.setVelocity(player.getLocation().getDirection().multiply(2));
        Enemy.setVelocity(getVelocity(0, 0, 3));
    }

    @Override
    protected void onDeactivate(PlayerInteractEvent event) {

    }
}
