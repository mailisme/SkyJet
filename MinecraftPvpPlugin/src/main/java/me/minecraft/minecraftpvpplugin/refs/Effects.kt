package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effects {
    val invisibleEffect = PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 0)
    val damageEffect = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 0)
    val speedEffect = PotionEffect(PotionEffectType.SPEED, 999999999, 5)
    val AllDie1 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 1)
    val AllDie2 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 2)
    val AllDie3 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 3)
}
