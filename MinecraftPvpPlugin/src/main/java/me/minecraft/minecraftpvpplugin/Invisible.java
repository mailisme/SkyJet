package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Invisible extends Gadget{

    public Invisible() {
        super(Material.STAINED_GLASS_PANE, "虛影斗篷", 5);
    }

    public void activate(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("activate");
    }

    public void deactivate(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("deactivate");
    }
}
