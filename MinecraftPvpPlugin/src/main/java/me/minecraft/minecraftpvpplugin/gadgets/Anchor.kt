package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent

object Anchor : Gadget(Material.ANVIL, "時空之錨", lore = listOf(
    "${ChatColor.YELLOW}使用時記住位置，再使用傳回",
    "${ChatColor.GRAY}傳送僅能使用一次"
), switchLike = true) {
    override fun onActivate(event: PlayerInteractEvent) {
        println("onActivate")
        val player = event.player
        LogWriter.log("${player.name} use 時空之錨 at ${player.location}")
        addPlayerData(player, "anviledLocation", player.location)
        val a = RunEvery(1.0){
            CustomEffect.playParticleInSphere(getPlayerData(player, "anviledLocation") as Location, Effect.ENDER_SIGNAL, 50, 0.8f, viewRadius = 10)
            println(getPlayerData(player, "anviledLocation"))
        }
        addPlayerData(player, "anchor stuff", a)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        val targetLocation = getPlayerData(player, "anviledLocation") as Location?
        LogWriter.log("${player.name} use 時空之錨 from ${player.location} to $targetLocation \n")
        player.teleport(targetLocation)
        (getPlayerData(player, "anchor stuff") as RunEvery).cancel()
    }

    override fun onGameEnd(event: PlayerChangedWorldEvent) {
        val player = event.player
        if(isActivating(player)) {
            (getPlayerData(player, "anchor stuff") as RunEvery).cancel()
        }
    }
}