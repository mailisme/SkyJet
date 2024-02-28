package me.minecraft.minecraftpvpplugin

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effect {
    val invisibleEffect = PotionEffect(PotionEffectType.INVISIBILITY, 100, 0)
    val damageEffect = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0)
    val speedEffect = PotionEffect(PotionEffectType.SPEED, 200, 1)
}
