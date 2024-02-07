package me.minecraft.minecraftpvpplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class RandomSpawnGadget {

    Map<World, Integer> SpawnerIndexes = new HashMap<>();

    void AddSpawnerToWorld (World world) {

        SpawnRandomGadget(world, 118.5, 98, 69.5);

        Random rand = new Random();
        Runnable spawner = new Runnable() {
            @Override
            public void run() {
                if (rand.nextFloat() > 0.9) {
                    float r = rand.nextFloat(9);

                    if (r > 8) {
                        SpawnRandomGadget(world, 124.5, 98, 66.5);
                    }
                    else if (r > 7) {
                        SpawnRandomGadget(world, 106.5, 101, 57.5);
                    }
                    else if (r > 6) {
                        SpawnRandomGadget(world, 140.5, 101, 50.5);
                    }
                    else if (r > 5) {
                        SpawnRandomGadget(world, 86.5, 98, 74.5);
                    }
                    else if (r > 4) {
                        SpawnRandomGadget(world, 118.5, 98, 54.5);
                    }
                    else if (r > 3) {
                        SpawnRandomGadget(world, 99.5, 101, 52.5);
                    }
                    else if (r > 2) {
                        SpawnRandomGadget(world, 107.5, 99, 99.5);
                    }
                    else if (r > 1) {
                        SpawnRandomGadget(world, 128.5, 101, 91.5);
                    }
                    else {
                        SpawnRandomGadget(world, 141.5, 100, 80.5);
                    }
                }
            }
        };

        int SpawnerIndex = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinecraftPvpPlugin.instance, spawner, 0, 20);

        SpawnerIndexes.put(world, SpawnerIndex);
    }

    void DeleteSpawnerFromWorld (World world) {
        Bukkit.getServer().getScheduler().cancelTask(SpawnerIndexes.get(world));
        SpawnerIndexes.remove(world);
    }

    void SpawnRandomGadget(World world, double x, double y, double z) {
        Random rand = new Random();
        float r = rand.nextFloat(7);
        ItemStack gadget;

        if (r > 6) {
            gadget = Gadgets.Anchor;
        }
        else if (r > 5) {
            gadget = Gadgets.Damage;
        }
        else if (r > 4) {
            gadget = Gadgets.Freeze;
        }
        else if (r > 3) {
            gadget = Gadgets.Invisible;
        }
        else if (r > 2) {
            gadget = Gadgets.KnockBack;
        }
        else if (r > 1) {
            gadget = Gadgets.Rebound;
        }
        else {
            gadget = Gadgets.Speed;
        }

        world.dropItem(new Location(world, x, y, z), gadget);
    }
}
