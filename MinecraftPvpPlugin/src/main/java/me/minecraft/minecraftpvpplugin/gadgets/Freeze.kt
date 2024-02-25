package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.MatWithData
import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.ThrowableGadget
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.util.Vector

class Freeze : ThrowableGadget(Material.SNOW_BALL, "冷陸氣團") {
    public override fun onHitObject(event: ProjectileHitEvent) {
        val player = event.entity.shooter as Player
        val locationMaterialMap: MutableMap<Location, MatWithData> = HashMap()

        val IceCenter = event.entity.location
        val EntityVelocity = event.entity.velocity
        IceCenter.add(EntityVelocity.normalize())
        val CenterBlock = IceCenter.block

        val BlockY = CenterBlock.y

        for (i in -3..3) {
            for (j in -3..3) {
                if (Vector(i, j, 0).length() < 2.7) {
                    val BlockX = CenterBlock.x + i
                    val BlockZ = CenterBlock.z + j

                    val block = IceCenter.world.getBlockAt(BlockX, BlockY, BlockZ)
                    if (block.type != Material.ICE) {
                        locationMaterialMap[block.location] = MatWithData(block.type, block.data)
                    }

                    block.type = Material.ICE
                }
            }
        }

        Bukkit.getScheduler().runTaskLater(MinecraftPvpPlugin.Companion.instance, {
            if (!locationMaterialMap.isEmpty()) {
                locationMaterialMap.forEach { (location: Location, matWithData: MatWithData) ->
                    IceCenter.world.getBlockAt(location).type = matWithData.material
                    IceCenter.world.getBlockAt(location).data = matWithData.data
                    locationMaterialMap.remove(location, matWithData)
                }
            }
        }, 40)
    }
}
