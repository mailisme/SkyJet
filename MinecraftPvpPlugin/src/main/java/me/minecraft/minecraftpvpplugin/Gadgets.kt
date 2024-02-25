package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.gadgets.*
import org.bukkit.inventory.ItemStack

object Gadgets {
    var Invisible: ItemStack? = Invisible().instance(1)
    var Damage: ItemStack? = Damage().instance(1)
    var Speed: ItemStack? = Speed().instance(1)
    var Rebound: ItemStack? = Rebound().instance(1)
    var Anchor: ItemStack? = Anchor().instance(1)
    var Freeze: ItemStack? = null

    init {
        try {
            Freeze = Freeze().instance(1)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    var KnockBack: ItemStack? = KnockBack().instance(1)
}
