package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Damage extends Gadget{
    public Damage() {
        super(Material.LAPIS_ORE, "劍魂之石", 10);
    }
    public void activate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.DamageEffect);
    }

    public void deactivate(PlayerInteractEvent event) {
    }
}
