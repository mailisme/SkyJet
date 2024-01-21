package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Speed extends Gadget{
    public Speed() {
        super(Material.LEATHER_BOOTS, "風行之靴", 10);
    }
    public void activate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.SpeedEffect);
    }

    public void deactivate(PlayerInteractEvent event) {
    }
}
