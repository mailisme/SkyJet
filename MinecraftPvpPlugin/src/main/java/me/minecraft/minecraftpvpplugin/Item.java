package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    static ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
    static ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);
    static ItemMeta SwordItemMeta = Item.DiamondSword.getItemMeta();
    static ItemStack IronHelmet = new ItemStack(Material.IRON_HELMET);
    static ItemStack IronChestplate = new ItemStack(Material.IRON_CHESTPLATE);
    static ItemStack IronLeggings = new ItemStack(Material.IRON_LEGGINGS);
    static ItemStack IronBoots = new ItemStack(Material.IRON_BOOTS);
}
