package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    static ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
    static ItemStack DiamondSword = new ItemStack(Material.DIAMOND_SWORD);

    static ItemStack DiamondPickaxe = new ItemStack(Material.DIAMOND_AXE);
    static ItemMeta SwordItemMeta = Items.DiamondSword.getItemMeta();
    static ItemStack IronHelmet = new ItemStack(Material.IRON_HELMET);
    static ItemStack IronChestplate = new ItemStack(Material.IRON_CHESTPLATE);
    static ItemStack IronLeggings = new ItemStack(Material.IRON_LEGGINGS);
    static ItemStack IronBoots = new ItemStack(Material.IRON_BOOTS);
    static ItemStack FishingRod = new ItemStack(Material.FISHING_ROD);
    static ItemStack Gapple = new ItemStack(Material.GOLDEN_APPLE, 5);
    static ItemStack Invisible = new ItemStack(Material.STAINED_GLASS_PANE);
    static ItemMeta InvisibleMeta = Invisible.getItemMeta();
}
