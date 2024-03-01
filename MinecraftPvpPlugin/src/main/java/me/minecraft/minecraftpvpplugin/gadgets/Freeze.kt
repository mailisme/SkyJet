package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.ThrowableGadget
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.util.Vector

class MatWithData(var material: Material, var data: Byte)

object Freeze : ThrowableGadget(Material.SNOW_BALL, "冷陸氣團") {
    public override fun onHitObject(event: ProjectileHitEvent) {
        val locationMaterialMap: MutableMap<Location, MatWithData> = HashMap()

        val iceCenter = event.entity.location
        val projectileVelocity = event.entity.velocity
        iceCenter.add(projectileVelocity.normalize())
        val centerBlock = iceCenter.block

        val blockY = centerBlock.y

        for (i in -3..3) {
            for (j in -3..3) {
                if (Vector(i, j, 0).length() < 2.7) {
                    val blockX = centerBlock.x + i
                    val blockZ = centerBlock.z + j

                    val block = iceCenter.world.getBlockAt(blockX, blockY, blockZ)
                    if (block.type != Material.ICE) {
                        locationMaterialMap[block.location] = MatWithData(block.type, block.data)
                    }

                    block.type = Material.ICE
                }
            }
        }

        Bukkit.getScheduler().runTaskLater(MinecraftPvpPlugin.instance, {
            locationMaterialMap.forEach { (location: Location, matWithData: MatWithData) ->
                val block = iceCenter.world.getBlockAt(location)

                block.type = matWithData.material
                block.data = matWithData.data
            }

            locationMaterialMap.clear()
        }, 40)
    }
}
