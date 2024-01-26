package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Rebound extends Gadget implements Listener {
    public Rebound() {
        super(Material.WOOD_DOOR, "反射之盾", 10);
    }
    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("start");
    }
    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event){
        Player player = (Player) event.getDamager();
        if(PlayersUsingGadget.contains((Player) event.getEntity())){
            int health = (int) player.getHealth();
            int hurt = (int) event.getDamage();
            player.setHealth(health - (double) hurt /2);
        }
    }

    public void onDeactivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("stop");
    }
}
