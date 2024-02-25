package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.gadgets.*
import org.bukkit.inventory.ItemStack

object Gadgets {
    var Invisible: ItemStack? = Invisible().create(1)
    var Damage: ItemStack? = Damage().create(1)
    var Speed: ItemStack? = Speed().create(1)
    var Rebound: ItemStack? = Rebound().create(1)
    var Anchor: ItemStack? = Anchor().create(1)
    var Freeze: ItemStack? = Freeze().create(1)

    var KnockBack: ItemStack? = KnockBack().create(1)
}
