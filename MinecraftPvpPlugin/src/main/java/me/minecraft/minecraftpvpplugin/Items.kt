package me.minecraft.minecraftpvpplugin

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object Items {
    var IronSword: ItemStack = ItemStack(Material.IRON_SWORD)
    var DiamondSword: ItemStack = ItemStack(Material.DIAMOND_SWORD)

    var DiamondPickaxe: ItemStack = ItemStack(Material.DIAMOND_AXE)
    var SwordItemMeta: ItemMeta = DiamondSword.itemMeta
    var IronHelmet: ItemStack = ItemStack(Material.IRON_HELMET)
    var IronChestplate: ItemStack = ItemStack(Material.IRON_CHESTPLATE)
    var IronLeggings: ItemStack = ItemStack(Material.IRON_LEGGINGS)
    var IronBoots: ItemStack = ItemStack(Material.IRON_BOOTS)
    var FishingRod: ItemStack = ItemStack(Material.FISHING_ROD)
    var Gapple: ItemStack = ItemStack(Material.GOLDEN_APPLE, 5)
}
