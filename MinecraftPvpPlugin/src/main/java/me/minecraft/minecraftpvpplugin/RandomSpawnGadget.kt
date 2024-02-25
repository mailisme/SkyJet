package me.minecraft.minecraftpvpplugin

import org.bukkit.*
import java.util.*

class RandomSpawnGadget {
    var SpawnerIndexes: MutableMap<World, Int> = HashMap()

    fun AddSpawnerToWorld(world: World) {
        SpawnRandomGadget(world, 118.5, 98.0, 69.5)

        val rand = Random()
        val spawner = Runnable {
            if (rand.nextFloat() > 0.9) {
                val r = rand.nextFloat(9f)

                if (r > 8) {
                    SpawnRandomGadget(world, 124.5, 98.0, 66.5)
                } else if (r > 7) {
                    SpawnRandomGadget(world, 106.5, 101.0, 57.5)
                } else if (r > 6) {
                    SpawnRandomGadget(world, 140.5, 101.0, 50.5)
                } else if (r > 5) {
                    SpawnRandomGadget(world, 86.5, 98.0, 74.5)
                } else if (r > 4) {
                    SpawnRandomGadget(world, 118.5, 98.0, 54.5)
                } else if (r > 3) {
                    SpawnRandomGadget(world, 99.5, 101.0, 52.5)
                } else if (r > 2) {
                    SpawnRandomGadget(world, 107.5, 99.0, 99.5)
                } else if (r > 1) {
                    SpawnRandomGadget(world, 128.5, 101.0, 91.5)
                } else {
                    SpawnRandomGadget(world, 141.5, 100.0, 80.5)
                }
            }
        }

        val SpawnerIndex = Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(MinecraftPvpPlugin.Companion.instance, spawner, 0, 20)

        SpawnerIndexes[world] = SpawnerIndex
    }

    fun DeleteSpawnerFromWorld(world: World) {
        if (SpawnerIndexes.containsKey(world)) {
            Bukkit.getServer().scheduler.cancelTask(SpawnerIndexes[world]!!)
            SpawnerIndexes.remove(world)
        }
    }

    fun SpawnRandomGadget(world: World, x: Double, y: Double, z: Double) {
        val rand = Random()
        val r = rand.nextFloat(7f)

        val gadget = if (r > 6) {
            Gadgets.Anchor
        } else if (r > 5) {
            Gadgets.Damage
        } else if (r > 4) {
            Gadgets.Freeze
        } else if (r > 3) {
            Gadgets.Invisible
        } else if (r > 2) {
            Gadgets.KnockBack
        } else if (r > 1) {
            Gadgets.Rebound
        } else {
            Gadgets.Speed
        }

        world.dropItem(Location(world, x, y, z), gadget)
    }
}
