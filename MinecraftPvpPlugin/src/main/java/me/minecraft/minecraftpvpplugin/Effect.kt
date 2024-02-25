package me.minecraft.minecraftpvpplugin

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effect {
    var InvisibleEffect = PotionEffect(PotionEffectType.INVISIBILITY, 100, 0)
    var DamageEffect = PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0)
    var SpeedEffect = PotionEffect(PotionEffectType.SPEED, 200, 1)
}
