package me.minecraft.minecraftpvpplugin;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.AnnotatedArrayType;
import java.util.*;


public class PvpPlace implements Listener {

    // Two lists so that the Players with the index 0 is at PVP0 world.
    static Player[][] world_of_players = new Player[3][2];

    // Adds Player to players list, and teleports them to the right world.
    public static void AddPlayer(Player player) {
        boolean FoundEmptyPlayerSlot = false;

        for (int WorldIndex = 0; WorldIndex < world_of_players.length; WorldIndex++) {
            World PVPWorld = MinecraftPvpPlugin.PVPWorlds.get(WorldIndex);

            if (world_of_players[WorldIndex][0] == null) {
                player.teleport(new Location(PVPWorld, 118.5, 98, 54.5));
                world_of_players[WorldIndex][0] = player;
                FoundEmptyPlayerSlot = true;
                break;
            } else if (world_of_players[WorldIndex][1] == null) {
                player.teleport(new Location(PVPWorld, 118.5, 98.0, 84.5, (float) 180, 0));
                world_of_players[WorldIndex][1] = player;
                GameStart(WorldIndex);
                FoundEmptyPlayerSlot = true;
                break;
            }
        }

        if (FoundEmptyPlayerSlot) {
            MinecraftPvpPlugin.ToPVP(player);
        }
        else {
            player.sendMessage("Server is full :(");
        }
    }

    public static void GameStart(int WorldIndex){

        TimerTask CountDown = new TimerTask() {
            int LeftSeconds = 3;
            @Override
            public void run() {
                World PVPWorld = MinecraftPvpPlugin.PVPWorlds.get(WorldIndex);

                world_of_players[WorldIndex][0].teleport(new Location(PVPWorld, 118.5, 98, 54.5));
                world_of_players[WorldIndex][1].teleport(new Location(PVPWorld, 118.5, 98.0, 84.5, (float) 180, 0));

                world_of_players[WorldIndex][0].sendTitle("The game will start in", String.valueOf(LeftSeconds));
                world_of_players[WorldIndex][1].sendTitle("The game will start in", String.valueOf(LeftSeconds));

                if (LeftSeconds == 0) {
                    world_of_players[WorldIndex][0].sendTitle("START", ":D");
                    world_of_players[WorldIndex][1].sendTitle("START", ":D");
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
        for (int WorldIndex = 0; WorldIndex < world_of_players.length; WorldIndex++) {
            for (int PlayerIndex = 0; PlayerIndex < 2; PlayerIndex++) {
                if (world_of_players[WorldIndex][PlayerIndex] == player) {
                    int AnotherPlayerIndex;
                    if (PlayerIndex == 0) {
                        AnotherPlayerIndex = 1;
                    } else {
                        AnotherPlayerIndex = 0;
                    }

                    Player AnotherPlayer = world_of_players[WorldIndex][AnotherPlayerIndex];

                    if (AnotherPlayer != null) {
                        Lose(player);
                        Win(AnotherPlayer);
                    }

                    if (player != null) {
                        player.teleport(Locations.lobby);
                        MinecraftPvpPlugin.ToLobby(player);
                    }

                    if (AnotherPlayer != null) {
                        AnotherPlayer.teleport(Locations.lobby);
                        MinecraftPvpPlugin.ToLobby(AnotherPlayer);
                    }

                    world_of_players[WorldIndex][0] = null;
                    world_of_players[WorldIndex][1] = null;

                    MinecraftPvpPlugin.PVPWorlds.get(WorldIndex).getEntities().forEach((e) -> {
                        if (e instanceof Item) {
                            e.remove();
                        }
                    });
                }
            }
        }
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
}
