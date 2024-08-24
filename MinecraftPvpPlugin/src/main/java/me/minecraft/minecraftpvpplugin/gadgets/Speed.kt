package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import me.minecraft.minecraftpvpplugin.refs.Effects
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

object Speed : Gadget(Material.LEATHER_BOOTS, "風行之靴", lore = listOf(
    "${ChatColor.YELLOW}賦予${ChatColor.AQUA}加速效果Ⅵ",
    "${ChatColor.GRAY}持續時間：10秒"
), duration = 10.0) {

    val map = hashMapOf<Player, RunEvery>()

    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 風行之靴")
        player.addPotionEffect(Effects.speed)
        map[player] = RunEvery(0.1){
            val loc = player.location.clone().add(0.0, -0.5, 0.0)
            CustomEffect.playParticleInSphere(loc, Effect.LAVA_POP, 10, 0.8f)
        }
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 風行之靴 disabled")
        player.removePotionEffect(Effects.speed.type)
        map[player]?.cancel()
    }
}
