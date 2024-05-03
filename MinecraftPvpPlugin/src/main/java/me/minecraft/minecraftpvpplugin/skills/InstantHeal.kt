package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.Skill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object InstantHeal : Skill(Material.EMERALD, "瞬間治癒") {
    public override fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        val rand = Random.nextInt(5, 10)
        if (player.health + rand > 20) {
            player.health -= (player.health + rand - 20)
        }
        else {
            player.health += rand
        }
    }
}