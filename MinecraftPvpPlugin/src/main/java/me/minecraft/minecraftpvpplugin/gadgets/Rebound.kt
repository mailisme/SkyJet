package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.CustomEffect
import me.minecraft.minecraftpvpplugin.Gadget
import me.minecraft.minecraftpvpplugin.LogWriter
import org.bukkit.ChatColor
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

object Rebound : Gadget(Material.WOOD_DOOR, "反射之盾", duration = 10.0, lore = listOf(
    "${ChatColor.YELLOW}反彈所受傷害的50%",
    "${ChatColor.GRAY}持續時間：10秒"
)), Listener {
    @EventHandler
    fun entityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val damager = event.damager as Player
            val damaged = event.entity as Player

            if (isActivating(damaged)) {
                LogWriter.log("${damaged.name} use 反射之盾 against ${damager.name}")

                CustomEffect.playParticleInSphere(damaged, Effect.FIREWORKS_SPARK, 100, 0.8f)
                val health = damager.health.toInt()
                val hurt = event.finalDamage.toInt()
                damager.health = health - hurt.toDouble() / 2
            }
        }
    }
}
