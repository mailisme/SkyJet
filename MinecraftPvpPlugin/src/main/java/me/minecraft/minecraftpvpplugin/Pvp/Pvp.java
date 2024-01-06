package me.minecraft.minecraftpvpplugin.Pvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Pvp {

    Location lobby = new Location(Bukkit.getWorld("Lobby"), 110.5, 74, 95.5);
    @EventHandler
    public void PvpChange(PlayerMoveEvent event){
        Player player = event.getPlayer();
        player.setInvulnerable(event.getTo() == lobby);
    }
}
