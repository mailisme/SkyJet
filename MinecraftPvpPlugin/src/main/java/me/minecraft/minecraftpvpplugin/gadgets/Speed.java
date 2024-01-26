package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Effect;
import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Speed extends Gadget {
    public Speed() {
        super(Material.LEATHER_BOOTS, "風行之靴", 10);
    }
    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.SpeedEffect);
    }

    public void onDeactivate(PlayerInteractEvent event) {
    }
}
