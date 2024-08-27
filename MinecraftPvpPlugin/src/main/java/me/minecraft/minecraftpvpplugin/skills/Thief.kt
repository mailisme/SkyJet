package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.*
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import net.minecraft.server.v1_8_R3.EnumParticle
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent


object Thief : Skill(Material.RAW_FISH, "扒手", coolDownSeconds = 10.0, lore = listOf(
    "${ChatColor.YELLOW}震出對手隨機的道具",
    "${ChatColor.GRAY}冷卻時間：10秒"
)) {
    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        val opponent = PvpPlaceManager.getOpponent(player) ?: return

        if (player.location.distance(opponent.location) < 5) {
            var gadgets = opponent.inventory.filterNotNull()
            gadgets = gadgets.filter {
                it.type == Gadgets.damage.material ||
                it.type == Gadgets.freeze.material ||
                it.type == Gadgets.speed.material ||
                it.type == Gadgets.rebound.material ||
                it.type == Gadgets.invisible.material ||
                it.type == Gadgets.knockBack.material
            }

            if (gadgets.isEmpty()) return

            val gadget = gadgets.random()

            LogWriter.log("${player.name} use 扒手 to get $gadget")

            CustomEffect.playParticle(player.location.clone().add(0.0, 2.2, 0.0), Effect.NOTE, 3, EffectShape.InGaussian, 0.4f)
            CustomEffect.playParticleWithPackets(opponent.location, EnumParticle.SUSPENDED_DEPTH, 200, EffectShape.InGaussianDisk, 0.5f, true)
            player.world.dropItem(opponent.location.add(opponent.location.direction.multiply(1.2)), gadget)
            opponent.inventory.remove(gadget)
        }
    }
}