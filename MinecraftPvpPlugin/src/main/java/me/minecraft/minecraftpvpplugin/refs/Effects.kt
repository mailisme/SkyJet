package me.minecraft.minecraftpvpplugin.refs

import me.minecraft.minecraftpvpplugin.skills.AllDie
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effects {
    val invisibleEffect = PotionEffect(PotionEffectType.INVISIBILITY, 100, 0)
    val damageEffect = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0)
    val speedEffect = PotionEffect(PotionEffectType.SPEED, 200, 1)
    val AllDie1 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 0)
    val AllDie2 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 1)
    val AllDie3 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 2)
}
