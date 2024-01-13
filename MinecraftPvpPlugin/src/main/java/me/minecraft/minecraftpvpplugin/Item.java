package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    static ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
    static ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
    static ItemMeta SwordItemMeta = Item.DiamondSword.getItemMeta();
}
