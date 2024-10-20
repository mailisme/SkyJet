package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.effect.CustomEffect
import me.minecraft.minecraftpvpplugin.helpers.LogWriter
import me.minecraft.minecraftpvpplugin.ThrowableGadget
import me.minecraft.minecraftpvpplugin.effect.InBall
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.util.Vector

class MatWithData(var material: Material, var data: Byte)

object Freeze : ThrowableGadget(Material.SNOW_BALL, "冷陸氣團", lore = listOf(
    "${ChatColor.YELLOW}投射時在撞擊點生成圓冰",
    "${ChatColor.GRAY}直徑：7格",
    "${ChatColor.GRAY}持續時間：2秒"
)) {

    private val snowballParticleTimerMap = hashMapOf<Entity, RunEveryFor>()

    public override fun onHitObject(event: ProjectileHitEvent) {
        val locationMaterialMap = hashMapOf<Location, MatWithData>()

        LogWriter.log("use 冷陸氣團 hit")

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

        (snowballParticleTimerMap[event.entity] as RunEveryFor).cancel()

        RunAfter(2.0) {
            for ((location, matWithData) in locationMaterialMap) {
                val block = iceCenter.world.getBlockAt(location)

                block.type = matWithData.material
                block.data = matWithData.data
            }

            locationMaterialMap.clear()
        }
    }

    public override fun onThrow(event: ProjectileLaunchEvent) {
        val item = event.entity
        snowballParticleTimerMap[item] = RunEveryFor(0.1){
            CustomEffect.playParticle(item.location, Effect.SNOW_SHOVEL, InBall(50, 0.8f))
        }
    }
}
