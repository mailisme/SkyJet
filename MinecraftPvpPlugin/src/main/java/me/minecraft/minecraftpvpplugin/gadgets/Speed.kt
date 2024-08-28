package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.effect.CustomEffect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.effect.InBall
import me.minecraft.minecraftpvpplugin.helpers.LogWriter
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import me.minecraft.minecraftpvpplugin.refs.Effects
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent

object Speed : Gadget(Material.LEATHER_BOOTS, "風行之靴", lore = listOf(
    "${ChatColor.YELLOW}賦予${ChatColor.AQUA}加速效果Ⅵ",
    "${ChatColor.GRAY}持續時間：10秒"
), duration = 10.0) {

    private val playerParticleTimerMap = hashMapOf<Player, RunEveryFor>()

    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 風行之靴")
        player.addPotionEffect(Effects.speed)
        playerParticleTimerMap[player] = RunEveryFor(0.1) {
            val loc = player.location.clone().add(0.0, -0.5, 0.0)
            CustomEffect.playParticle(loc, Effect.LAVA_POP, InBall(10, 0.8f))
        }
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 風行之靴 disabled")
        player.removePotionEffect(Effects.speed.type)
        playerParticleTimerMap[player]?.cancel()
    }
    override fun onGameEnd(event: PlayerChangedWorldEvent) {
        val player = event.player
        if(isActivating(player)) {
            playerParticleTimerMap[player]?.cancel()
        }
    }
}
