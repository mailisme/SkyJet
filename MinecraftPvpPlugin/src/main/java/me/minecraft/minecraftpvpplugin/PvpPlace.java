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
            return;
        }

        if (GetPlayerByIndex(players0, PlayerIndex) == player) {
            if (Win(GetPlayerByIndex(players1, PlayerIndex))) {
                Lose(player);
            }
        }

        else {
            if(Win(GetPlayerByIndex(players0, PlayerIndex))) {
                Lose(player);
            }
        }

        player.getWorld().getEntities().forEach((e) -> {
            if (e instanceof Item) {
                e.remove();
            }
        });

        TeleportBackToLobby(GetPlayerByIndex(players0, PlayerIndex));
        TeleportBackToLobby(GetPlayerByIndex(players1, PlayerIndex));
    }

    static boolean Lose(Player player) {
        if (player != null) {
            player.sendTitle(ChatColor.AQUA + "You Lose", ChatColor.DARK_BLUE + ":(");
            return true;
        }
        return false;
    }

    static boolean Win(Player player) {
        if (player != null) {
            player.sendTitle(ChatColor.GOLD + "You Win !!", ChatColor.RED + ":D");
            return true;
        }
        return false;
    }

    static Player GetPlayerByIndex(List<Player> player, int index) {
        if (index < player.size()) {
            return player.get(index);
        }
        else {
            return null;
        }
    }

    static void TeleportBackToLobby(Player player) {
        if (player != null) {
            player.teleport(Locations.lobby);
            MinecraftPvpPlugin.ToLobby(player);
            players0.remove(player);
            players1.remove(player);

            PvpPlayerCount -= 1;
        }
    }
}
