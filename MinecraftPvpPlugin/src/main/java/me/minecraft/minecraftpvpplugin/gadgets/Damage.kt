package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.EffectShape
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import me.minecraft.minecraftpvpplugin.refs.Effects
import net.minecraft.server.v1_8_R3.EnumParticle
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent

object Damage : Gadget(Material.LAPIS_ORE, "劍魂之石", lore = listOf(
    "${ChatColor.YELLOW}賦予${ChatColor.AQUA}增加攻擊力Ⅰ",
    "${ChatColor.GRAY}持續時間：10秒"
), duration = 10.0) {
    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} use 劍魂之石")
        player.addPotionEffect(Effects.damage)
        val particleTimer = RunEveryFor(0.1){
            CustomEffect.playParticleWithPackets(player, EnumParticle.FLAME, 10, EffectShape.InGaussian, 0.5f,true, 1.0f)
        }
        addPlayerData(player, "damage", particleTimer)
    }

    override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        LogWriter.log("${player.name} 劍魂之石 disabled")
        player.removePotionEffect(Effects.damage.type)
        (getPlayerData(player, "damage") as RunEveryFor).cancel()
    }
    override fun onGameEnd(event: PlayerChangedWorldEvent) {
        val player = event.player
        if(isActivating(player)) {
            (getPlayerData(player, "damage") as RunEveryFor).cancel()
        }
    }
}
