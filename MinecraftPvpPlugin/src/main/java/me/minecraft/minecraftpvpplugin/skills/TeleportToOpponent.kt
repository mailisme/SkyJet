package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.*
import me.minecraft.minecraftpvpplugin.helpers.Countdown
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
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
        val playerName = player.name


        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        LogWriter.log("${player.name} use 閃電突襲")

        val opponent = PvpPlaceManager.getOpponent(player)

        object : Countdown(listOf(player), "Teleport in ", "Here u go") {
            override fun onCountdown() {
                CustomEffect.playParticleInSphere(player, Effect.PORTAL, 1000, 0.8f)
            }

            override fun onCountdownEnd() {
                val targetLocation = opponent!!.location
                    .subtract(
                        player.location.direction
                            .multiply(Vector(1, 0, 1))
                            .normalize()
                            .multiply(2)
                    )

                targetLocation.setDirection(
                    opponent.location
                        .toVector()
                        .subtract(player.location.toVector())
                        .normalize()
                )

                player.teleport(targetLocation)
            }
        }

        object : Countdown(listOf(opponent) as List<Player>, "$playerName is teleporting to u in ", "") {}
    }
}