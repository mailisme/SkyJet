package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.EffectShape
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.Skill
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

object InstantHeal : Skill(Material.EMERALD, "瞬間治癒", coolDownSeconds = 5.0, lore = listOf(
    "${ChatColor.YELLOW}恢復玩家所缺少血量的50%",
    "${ChatColor.GRAY}冷卻時間：5秒"
)) {
    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player

        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        val prevHealth = player.health
        player.health += (20 - player.health) * 0.5
        CustomEffect.playParticle(player, Effect.HEART, 8, EffectShape.InGaussian, 0.5f)

        LogWriter.log("${player.name} $prevHealth use 瞬間治癒成 ${player.health}")
    }
}