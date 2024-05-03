package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object InstantHeal : ItemStack(), Listener {
    init {
        this.type = Material.POTION

        val meta = this.itemMeta
        meta.displayName = "InstantHeal"
        this.setItemMeta(meta)

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        if (event.item == InstantHeal) {
            val player = event.player
            val rand = Random.nextInt(5, 10)
            if (player.health + rand > 20) {
                player.health -= rand
            }
            else {
                player.health += rand
            }
        }
    }
    fun create(amount: Int = 1): ItemStack {
        val stack = this.clone()
        stack.amount = amount
        return stack
    }
}