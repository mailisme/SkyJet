package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class Anchor extends Gadget {
    Map<Player, Location> AnviledLocation = new HashMap<Player, Location>();

    public Anchor() {
        super(Material.ANVIL, "時空之錨",true);
    }

    @Override
    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        AnviledLocation.put(player, player.getLocation());
    }

    @Override
    public void onDeactivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.teleport(AnviledLocation.get(player));
        AnviledLocation.remove(player);
    }
}
