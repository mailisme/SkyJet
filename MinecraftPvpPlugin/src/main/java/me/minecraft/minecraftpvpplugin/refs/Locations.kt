package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

object Locations {
    val lobbySpawn = Location(Worlds.lobby, 178.0, 104.5, 114.0)
    private val pvpBorder0 = Vector(37, 97, -7)
    private val pvpBorder1 = Vector(195, 138, 152)
    val  pvpPortalBottom = Vector(118, 98, 69)
    val pvpPortalTop = Vector(118, 113, 69)
    class PvpSpawn0(world: World) : Location(world, 118.5, 98.0, 54.5)
    class PvpSpawn1(world: World) : Location(world, 118.5, 98.0, 84.5, 180f, 0f)

    val pvpGadgetSpawnPoints = buildList {
        for (y in pvpBorder0.y.toInt()..pvpBorder1.y.toInt()) {
            for (x in pvpBorder0.x.toInt()..pvpBorder1.x.toInt()) {
                for (z in pvpBorder0.z.toInt()..pvpBorder1.z.toInt()) {
                    val block = Worlds.pvpWorldStandard.getBlockAt(x, y, z)
                    val blockAbove = Worlds.pvpWorldStandard.getBlockAt(x, y + 1, z)

                    if (!block.isEmpty && !block.isLiquid && blockAbove.isEmpty) {
                        add(Vector(x, y + 1, z))
                    }
                }
            }
        }
    }
}
