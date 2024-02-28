package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class RandomSpawnGadget(private val world: World) {
    private var spawnerIndex = 0

    fun start() {
        spawnRandomGadget(world, 118.5, 98.0, 69.5)

        val rand = Random()
        val spawner = Runnable {
            if (rand.nextFloat() > 0.9) {
                val r = rand.nextFloat(9f)

                if (r > 8) {
                    spawnRandomGadget(world, 124.5, 98.0, 66.5)
                } else if (r > 7) {
                    spawnRandomGadget(world, 106.5, 101.0, 57.5)
                } else if (r > 6) {
                    spawnRandomGadget(world, 140.5, 101.0, 50.5)
                } else if (r > 5) {
                    spawnRandomGadget(world, 86.5, 98.0, 74.5)
                } else if (r > 4) {
                    spawnRandomGadget(world, 118.5, 98.0, 54.5)
                } else if (r > 3) {
                    spawnRandomGadget(world, 99.5, 101.0, 52.5)
                } else if (r > 2) {
                    spawnRandomGadget(world, 107.5, 99.0, 99.5)
                } else if (r > 1) {
                    spawnRandomGadget(world, 128.5, 101.0, 91.5)
                } else {
                    spawnRandomGadget(world, 141.5, 100.0, 80.5)
                }
            }
        }

        spawnerIndex =
            Bukkit.getServer().scheduler.scheduleSyncRepeatingTask(MinecraftPvpPlugin.instance, spawner, 0, 20)
    }

    fun stop() {
        Bukkit.getServer().scheduler.cancelTask(spawnerIndex)
    }

    private fun spawnRandomGadget(world: World, x: Double, y: Double, z: Double) {
        val rand = Random()
        val r = rand.nextFloat(7f)

        val gadget = if (r > 6) {
            Gadgets.anchor
        } else if (r > 5) {
            Gadgets.damage
        } else if (r > 4) {
            Gadgets.freeze
        } else if (r > 3) {
            Gadgets.invisible
        } else if (r > 2) {
            Gadgets.knockBack
        } else if (r > 1) {
            Gadgets.rebound
        } else {
            Gadgets.speed
        }

        world.dropItem(Location(world, x, y, z), gadget)
    }
}
