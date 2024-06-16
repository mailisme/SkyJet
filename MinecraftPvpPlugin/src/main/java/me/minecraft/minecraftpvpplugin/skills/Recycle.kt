package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import me.minecraft.minecraftpvpplugin.refs.Items
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.random.Random

object Recycle : Skill(Material.GOLD_RECORD, "回收再利用"){
    @EventHandler
    public fun ItemsUsed(event: PlayerInteractEvent) {
        val player = event.player
        if (!super.isTriggerActivateSuccessful(player)) return
        val item = event.item
        if (item.type == Gadgets.anchor.material ||
            item.type == Gadgets.damage.material ||
            item.type == Gadgets.freeze.material ||
            item.type == Gadgets.speed.material ||
            item.type == Gadgets.rebound.material ||
            item.type == Gadgets.invisible.material ||
            item.type == Gadgets.knockBack.material) {
            val list = listOf(0, 1)
            val randomIndex = Random.nextInt(list.size);
            val randomElement = list[randomIndex]
            if (randomElement == 1) {
                val I = item.clone()
                I.amount = 1
                player.inventory.addItem(I)
            }
            else{
                val list = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
                val randomIndex = Random.nextInt(list.size);
                val randomElement = list[randomIndex]
                if (randomElement == 1 ) {
                    val I = item.clone()
                    I.amount = 1
                    PvpPlaceManager.getOpponent(player)?.inventory?.addItem(item)
                }
            }
        }
    }
}