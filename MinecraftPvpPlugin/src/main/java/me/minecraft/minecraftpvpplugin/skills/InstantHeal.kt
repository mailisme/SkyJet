package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.Skill
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.random.Random

object InstantHeal : Skill(Material.EMERALD, "瞬間治癒", 5.0) {
    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player

        if (!super.isClickEventClickingItself(event)) return
        if (!super.isTriggerActivateSuccessful(player)) return

        val rand = Random.nextInt(5, 10)

        if (player.health + rand > 20) {
            player.health -= (player.health + rand - 20)
        } else {
            player.health += rand
        }
    }
}