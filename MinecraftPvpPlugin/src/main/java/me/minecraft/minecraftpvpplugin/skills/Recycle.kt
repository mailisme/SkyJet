package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.random.Random

object Recycle : Skill(Material.GOLD_RECORD, "回收再利用") {
    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        if (!super.isTriggerActivateSuccessful(player)) return
        val item = event.item
        if (item.type == Gadgets.anchor.material ||
            item.type == Gadgets.damage.material ||
            item.type == Gadgets.freeze.material ||
            item.type == Gadgets.speed.material ||
            item.type == Gadgets.rebound.material ||
            item.type == Gadgets.invisible.material ||
            item.type == Gadgets.knockBack.material
        ) {
            if (Random.nextFloat() < 1 / 2) {
                val newItem = item.clone()
                newItem.amount = 1
                player.inventory.addItem(newItem)
            } else {
                if (Random.nextFloat() < 1 / 20) {
                    val newItem = item.clone()
                    newItem.amount = 1
                    PvpPlaceManager.getOpponent(player)?.inventory?.addItem(item)
                }
            }
        }
    }
}