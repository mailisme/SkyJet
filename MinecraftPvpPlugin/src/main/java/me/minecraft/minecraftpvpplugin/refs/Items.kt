package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object Items {
    var ironSword: ItemStack = ItemStack(Material.IRON_SWORD)
    var diamondSword: ItemStack = ItemStack(Material.DIAMOND_SWORD)

    var diamondPickaxe: ItemStack = ItemStack(Material.DIAMOND_AXE)
    var swordItemMeta: ItemMeta = diamondSword.itemMeta

    var ironHelmet: ItemStack = ItemStack(Material.IRON_HELMET)
    var ironChestplate: ItemStack = ItemStack(Material.IRON_CHESTPLATE)
    var ironLeggings: ItemStack = ItemStack(Material.IRON_LEGGINGS)
    var ironBoots: ItemStack = ItemStack(Material.IRON_BOOTS)

    var leatherHelmet: ItemStack = ItemStack(Material.LEATHER_HELMET)
    var leatherChestplate: ItemStack = ItemStack(Material.LEATHER_CHESTPLATE)
    var leatherLeggings: ItemStack = ItemStack(Material.LEATHER_LEGGINGS)
    var leatherBoots: ItemStack = ItemStack(Material.LEATHER_BOOTS)

    var fishingRod: ItemStack = ItemStack(Material.FISHING_ROD)
    var gapple: ItemStack = ItemStack(Material.GOLDEN_APPLE, 5)
}
