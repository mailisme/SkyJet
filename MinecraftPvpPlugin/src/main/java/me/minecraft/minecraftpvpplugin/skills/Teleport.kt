package me.minecraft.minecraftpvpplugin.skills

import me.minecraft.minecraftpvpplugin.Skill
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class Teleport(material: Material?, name: String?) : Skill(material, name) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
    }
}
