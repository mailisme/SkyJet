package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.refs.Gadgets
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.random.Random

object Recycle : Skill(Material.GOLD_RECORD, "回收再利用"){
    public override fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        if(event.item == Gadgets.anchor) {
            if (Random.nextInt(0, 1) == 0) {
                if (Random.nextInt(1, 20) == 1) {

                }
            }
            else {

            }
        }
    }
}