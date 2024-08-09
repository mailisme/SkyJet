package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import java.io.File


data object Worlds {
    var lobby: World = Bukkit.createWorld(WorldCreator("Lobby"))
    var pvpWorldStandard: World = Bukkit.createWorld(WorldCreator("PVP_standard"))
    var pvpWorlds: MutableList<World> = mutableListOf()

    init {
        var n = 0
        while (true) {
            val f = File("PVP_$n")
            if (f.exists()) f.deleteRecursively()
            else break
            n ++
        }
    }

    fun isInPvp(player: Player): Boolean {
        return pvpWorlds.contains(player.world)
    }

    fun isInLobby(player: Player): Boolean {
        return player.world == lobby
    }

    fun newPvpWorld(): World {
        val worldName = "PVP_${pvpWorlds.count()}"
        File("PVP_standard/").copyRecursively(File("$worldName/"))
        File("$worldName/uid.dat").delete()
        val world = Bukkit.createWorld(WorldCreator(worldName))
        pvpWorlds.addLast(world)

        return world
    }
}
