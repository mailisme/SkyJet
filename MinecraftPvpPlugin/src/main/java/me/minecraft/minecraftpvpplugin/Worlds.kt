package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.Player


data object Worlds {
    var lobby: World = Bukkit.createWorld(WorldCreator("Lobby"))
    var pvpWorlds: List<World> = List(3) { Bukkit.createWorld(WorldCreator("PVP$it")) }

    fun isInPvp(player: Player): Boolean {
        return pvpWorlds.contains(player.world)
    }

    fun isInLobby(player: Player): Boolean {
        return player.world == lobby
    }
}
