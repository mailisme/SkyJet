package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import me.minecraft.minecraftpvpplugin.helpers.RunAfter
import me.minecraft.minecraftpvpplugin.helpers.RunEvery
import net.minecraft.server.v1_8_R3.EnumParticle
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

object KnockBack : Gadget(material = Material.FIREBALL, "地球之心", lore = listOf(
    "${ChatColor.YELLOW}震走半徑6格內的實體",
), duration = 0.0) {
    lateinit var a:RunEvery
    var i = 1f

    fun  check(a: RunEvery){
        if (i > 5) {

        }
    }

    override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player as Player
        val nearByEntities = player.getNearbyEntities(6.0, 6.0, 6.0)
        LogWriter.log("${player.name} use 地球之心")

        a = RunEvery(0.1){
            CustomEffect.playParticleInSphereWithPackets(player, EnumParticle.DRIP_LAVA, 1000, i.toFloat(), true, 1f)
            i++
        }


        for (entity in nearByEntities) {
            val entityPos = entity.location.toVector()
            val playerPos = player.location.toVector()
            entity.velocity = entityPos
                .subtract(playerPos)
                .setY(0)
                .normalize()
                .multiply(2)
                .add(Vector(0.0, 0.7, 0.0))
        }
    }
}
