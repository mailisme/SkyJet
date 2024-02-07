package me.minecraft.minecraftpvpplugin;

import me.minecraft.minecraftpvpplugin.gadgets.*;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Gadgets {
    static ItemStack Invisible = new Invisible().instance(1);
    static ItemStack Damage = new Damage().instance(1);
    static ItemStack Speed = new Speed().instance(1);
    static ItemStack Rebound = new Rebound().instance(1);
    static ItemStack Anchor = new Anchor().instance(1);
    static ItemStack Freeze;

    static {
        try {
            Freeze = new Freeze().instance(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static ItemStack KnockBack = new KnockBack().instance(1);
}
