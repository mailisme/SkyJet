package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.Effect
import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class Speed : Gadget(Material.LEATHER_BOOTS, "風行之靴", 10) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.addPotionEffect(Effect.SpeedEffect)
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
    }
}
