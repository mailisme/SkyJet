package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

object TeleportToOpponent :
    Skill(Material.REDSTONE_BLOCK, "閃電突襲", coolDownSeconds = 10.0, ignoreCoolDownSeconds = 1.0, lore = listOf(
        "${ChatColor.YELLOW}瞬移到對手旁",
        "${ChatColor.GRAY}冷卻時間：10秒",
        "${ChatColor.GRAY}第一次觸發1秒內忽略冷卻",
        "${ChatColor.GRAY}位置：對手位置往面朝反方向數2格",
        "${ChatColor.GRAY}方向：面向對手"
    )) {
    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player

        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        val opponentLocation = PvpPlaceManager.getOpponent(player)!!.location

        val targetLocation = opponentLocation
            .subtract(
                player.location.direction
                    .multiply(Vector(1, 0, 1))
                    .normalize()
                    .multiply(2)
            )

        targetLocation.setDirection(
            opponentLocation
                .toVector()
                .subtract(player.location.toVector())
                .normalize()
        )

        player.teleport(targetLocation)
    }
}