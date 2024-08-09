package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effects {
    val invisible = PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 0)
    val damage = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 0)
    val speed = PotionEffect(PotionEffectType.SPEED, 999999999, 5)
    val weakness1 = PotionEffect(PotionEffectType.WEAKNESS, 999999999, 0)
    val weakness2 = PotionEffect(PotionEffectType.WEAKNESS, 999999999, 1)
    val weakness3= PotionEffect(PotionEffectType.WEAKNESS, 999999999, 2)
//    val AllDie1 = PotionEffect(PotionEffectType.DAM, 999999999, 1)
//    val AllDie2 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 2)
//    val AllDie3 = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 3)
}
