package me.minecraft.minecraftpvpplugin.skills;

import me.minecraft.minecraftpvpplugin.Skill;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Teleport extends Skill{
    public Teleport(Material material, String name) {
        super(material, name);
    }

    @Override
    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
    }
}
