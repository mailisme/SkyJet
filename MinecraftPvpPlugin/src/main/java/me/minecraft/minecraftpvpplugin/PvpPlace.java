package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;


public class PvpPlace implements Listener {
    static List<Player> players0 = new ArrayList<>();
    static List<Player> players1 = new ArrayList<>();

    static int PvpPlayerCount;

    public static void AddPlayer(Player player) {
        Location pvp;

        if (PvpPlayerCount < MinecraftPvpPlugin.PVPWorlds.size() * 2) {
            if (PvpPlayerCount % 2 == 0) {
                players0.add(player);
                int WorldIndex = players0.indexOf(player);
                pvp = new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98, 54.5);
            }

            else {
                players1.add(player);
                int WorldIndex = players1.indexOf(player);
                pvp = new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98.0, 84.5, (float) 180, 0);
            }

            PvpPlayerCount += 1;

            player.teleport(pvp);
            MinecraftPvpPlugin.ToPVP(player);
        }

        else {
            player.sendMessage("Server full :(");
        }
    }

    public static void RemovePlayer(Player player) {
        int PlayerIndex = Math.max(players0.indexOf(player), players1.indexOf(player));

        if (PlayerIndex == -1) {
            player.sendMessage("You are already in lobby!");
        }

        if (PlayerIndex < players0.size()) {
            players0.get(PlayerIndex).teleport(Locations.lobby);
            MinecraftPvpPlugin.ToLobby(players0.get(PlayerIndex));
            players0.remove(PlayerIndex);

            PvpPlayerCount -= 1;
        }

        if (PlayerIndex < players1.size()) {
            players1.get(PlayerIndex).teleport(Locations.lobby);
            MinecraftPvpPlugin.ToLobby(players1.get(PlayerIndex));
            players1.remove(PlayerIndex);

            PvpPlayerCount -= 1;
        }
        player.getWorld().getEntities().remove(player.getWorld().getEntities());
    }
}
