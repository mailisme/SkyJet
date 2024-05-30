package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.RunAperiodically
import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import me.minecraft.minecraftpvpplugin.helpers.WeightedRandomChooser
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import me.minecraft.minecraftpvpplugin.refs.Locations
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class RandomSpawnGadget(private val world: World) {
    private lateinit var spawner: RunAperiodically

    private val randomSpawnLocationChooser = WeightedRandomChooser<Vector>().addChoices(Locations.pvpGadgetSpawnPoints)

    private val randomGadgetChooser = WeightedRandomChooser<ItemStack>()
        .addChoice(Gadgets.anchor)
        .addChoice(Gadgets.damage)
        .addChoice(Gadgets.freeze)
        .addChoice(Gadgets.invisible)
        .addChoice(Gadgets.knockBack)
        .addChoice(Gadgets.rebound)
        .addChoice(Gadgets.speed)


    fun start() {
        println("start spawning")

        spawnRandomGadget(Location(world, 118.5, 98.0, 69.5))

        spawner = RunAperiodically(2, 5) {
            println("spawn gadget")
            spawnRandomGadget(randomSpawnLocationChooser.choose().toLocation(world))
        }
    }

    fun stop() {
        spawner.cancel()
    }

    private fun spawnRandomGadget(location: Location) {
        world.dropItem(location, randomGadgetChooser.choose())
    }
}
