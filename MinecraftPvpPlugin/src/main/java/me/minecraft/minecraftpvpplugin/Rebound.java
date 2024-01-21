package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Rebound extends Gadget implements Listener {
    public Rebound() {
        super(Material.WOOD_DOOR, "反射之盾", 10);
    }
    public void activate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("start");
    }
    @EventHandler
    public void PlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        if(PlayersUsingGadget.contains((Player) event.getRightClicked())){
            int health = (int) player.getHealth();
            player.setHealth(health - 2);
        }
    }

    public void deactivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("stop");
    }
}
