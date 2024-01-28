package me.minecraft.minecraftpvpplugin;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effect {
    public static PotionEffect InvisibleEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0);
    public static PotionEffect DamageEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0);
    public static PotionEffect SpeedEffect = new PotionEffect(PotionEffectType.SPEED, 200, 1);
    public static PotionEffect FreezeEffect = new PotionEffect(PotionEffectType.SLOW, 100, 100);
    public static PotionEffect UnjumpableEffect = new PotionEffect(PotionEffectType.JUMP, 100, -100);
}
