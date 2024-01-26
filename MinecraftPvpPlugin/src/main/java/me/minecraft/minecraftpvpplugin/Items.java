package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    public static ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
    public static ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);

    public static ItemStack DiamondPickaxe = new ItemStack(Material.DIAMOND_AXE);
    public static ItemMeta SwordItemMeta = Items.DiamondSword.getItemMeta();
    public static ItemStack IronHelmet = new ItemStack(Material.IRON_HELMET);
    public static ItemStack IronChestplate = new ItemStack(Material.IRON_CHESTPLATE);
    public static ItemStack IronLeggings = new ItemStack(Material.IRON_LEGGINGS);
    public static ItemStack IronBoots = new ItemStack(Material.IRON_BOOTS);
    public static ItemStack FishingRod = new ItemStack(Material.FISHING_ROD);
    public static ItemStack Gapple = new ItemStack(Material.GOLDEN_APPLE, 5);
}
