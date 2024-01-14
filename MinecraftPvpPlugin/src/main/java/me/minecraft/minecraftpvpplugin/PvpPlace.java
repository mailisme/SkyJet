package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class PvpPlace implements Listener {

    // Two lists so that the Players with the index 0 is at PVP0 world.
    static List<Player> players0 = new ArrayList<>();
    static List<Player> players1 = new ArrayList<>();

    static int TotalPvpPlayerCount;

    // Adds Player to players list, and teleports them to the right world.
    public static void AddPlayer(Player player) {
        Location pvp;

        if (TotalPvpPlayerCount < MinecraftPvpPlugin.PVPWorlds.size() * 2) { // Check if the server is full. (empty player slots = num of PVP worlds x 2)
            TotalPvpPlayerCount += 1;

            if (TotalPvpPlayerCount % 2 == 1) {
                players0.add(player);
                int WorldIndex = players0.indexOf(player);
                pvp = new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98, 54.5);
            }

            else {
                players1.add(player);
                int WorldIndex = players1.indexOf(player);
                pvp = new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98.0, 84.5, (float) 180, 0);

                GameStart(WorldIndex); // If the total player num is even (meaning that there is a new pair of players), start the game.
            }


            player.teleport(pvp);
            MinecraftPvpPlugin.ToPVP(player);
        }

        else {
            player.sendMessage("Server full :(");
        }
    }

    public static void GameStart(int WorldIndex){

        TimerTask CountDown = new TimerTask() {
            int LeftSeconds = 3;
            @Override
            public void run() {
                players0.get(WorldIndex).teleport(new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98, 54.5));
                players1.get(WorldIndex).teleport(new Location(MinecraftPvpPlugin.PVPWorlds.get(WorldIndex), 118.5, 98.0, 84.5, (float) 180, 0));

                players0.get(WorldIndex).sendTitle("The game will start in", String.valueOf(LeftSeconds));
                players1.get(WorldIndex).sendTitle("The game will start in", String.valueOf(LeftSeconds));

                if (LeftSeconds == 0) {
                    this.cancel();
                }

                LeftSeconds -= 1;
            }
        };

        Timer t = new Timer();
        t.schedule(CountDown, 0, 1000);
    }


    // Marks input Player as loser, and the opponent of the Player as winner. Remove them from the players list and teleport them back to lobby.
    public static void RemovePlayer(Player player) {
        // This will be the player index or -1 if player isn't in either of the lists.
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
            int WorldIndex = players0.indexOf(player);
            player.teleport(Locations.lobby);
            MinecraftPvpPlugin.ToLobby(player);
            players0.set(WorldIndex, null);
            players1.set(WorldIndex, null);

            TotalPvpPlayerCount -= 1;
        }
    }
}
