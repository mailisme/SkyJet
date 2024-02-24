package me.minecraft.minecraftpvpplugin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class GameLoop {
    static long PvpTime = 30;
    static long NoPvpTime = 30;

    Map<World, Timer> GameLoops = new HashMap<>();

    void AddGameLoopToWorld (World world) {
        Timer t = new Timer();

        world.setPVP(false);
        TimerTask SetPvp = new TimerTask() {
            @Override
            public void run() {
                Timer _t = new Timer();

                TimerTask CountDown = new TimerTask() {
                    int LeftSeconds = 3;
                    @Override
                    public void run() {
                        for (Player player: world.getPlayers()) {
                            if (LeftSeconds <= 0) {
                                player.sendTitle("START", ":D");
                                world.setPVP(true);

                                _t.cancel();
                            }
                            else {
                                player.sendTitle("PVP will start in", String.valueOf(LeftSeconds));
                            }
                        }

                        LeftSeconds -= 1;
                    }
                };

                _t.schedule(CountDown, 0, 1000);
            }
        };

        TimerTask SetNoPvp = new TimerTask() {
            @Override
            public void run() {
                Timer _t = new Timer();

                TimerTask CountDown = new TimerTask() {
                    int LeftSeconds = 3;
                    @Override
                    public void run() {
                        for (Player player: world.getPlayers()) {
                            if (LeftSeconds <= 0) {
                                player.sendTitle("SEARCH FOR GADGETS!", ":D");
                                world.setPVP(false);

                                _t.cancel();
                            }
                            else {
                                player.sendTitle("PVP will end in", String.valueOf(LeftSeconds));
                            }
                        }

                        LeftSeconds -= 1;
                    }
                };

                _t.schedule(CountDown, 0, 1000);
            }
        };

        t.schedule(SetPvp, (PvpTime - 3) * 1000, (PvpTime + NoPvpTime) * 1000);
        t.schedule(SetNoPvp, (PvpTime + NoPvpTime - 3) * 1000, (PvpTime + NoPvpTime) * 1000);

        GameLoops.put(world, t);
    }
    void DeleteGameLoopFromWorld (World world) {
        if (GameLoops.containsKey(world)) {
            GameLoops.get(world).cancel();
            GameLoops.remove(world);
        }
    }
}
