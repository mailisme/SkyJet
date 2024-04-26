package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import me.minecraft.minecraftpvpplugin.helpers.WeightedRandomChooser
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import java.util.*

class RandomSpawnGadget(private val world: World) {
    private var rand = Random()
    private lateinit var spawner: RunEvery

    private val randomSpawnLocationChooser = WeightedRandomChooser<Location>()
        .addChoice(Location(world, 124.5, 98.0, 66.5))
        .addChoice(Location(world, 140.5, 101.0, 50.5))
        .addChoice(Location(world, 86.5, 98.0, 74.5))
        .addChoice(Location(world, 118.5, 98.0, 54.5))
        .addChoice(Location(world, 99.5, 101.0, 52.5))
        .addChoice(Location(world, 107.5, 99.0, 99.5))
        .addChoice(Location(world, 128.5, 101.0, 91.5))
        .addChoice(Location(world, 141.5, 100.0, 80.5))

    private val randomGadgetChooser = WeightedRandomChooser<ItemStack>()
        .addChoice(Gadgets.anchor)
        .addChoice(Gadgets.damage)
        .addChoice(Gadgets.freeze)
        .addChoice(Gadgets.invisible)
        .addChoice(Gadgets.knockBack)
        .addChoice(Gadgets.rebound)
        .addChoice(Gadgets.speed)


    fun start() {
        spawnRandomGadget(Location(world, 118.5, 98.0, 69.5))

        spawner = RunEvery(1) {
            if (rand.nextFloat() > 0.9) {
                spawnRandomGadget(randomSpawnLocationChooser.choose())
            }
        }
    }

    fun stop() {
        spawner.cancel()
    }

    private fun spawnRandomGadget(location: Location) {
        world.dropItem(location, randomGadgetChooser.choose())
    }
}
