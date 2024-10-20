package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.effect.CustomEffect
import me.minecraft.minecraftpvpplugin.helpers.LogWriter
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.effect.InGaussian
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

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
        CustomEffect.playParticle(player, Effect.HEART, InGaussian(8, Vector(0.5, 0.5, 0.5)))

        LogWriter.log("${player.name} $prevHealth use 瞬間治癒成 ${player.health}")
    }
}