        package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta


        object  Items {
    val ironSword: ItemStack = ItemStack(Material.IRON_SWORD)
    val compass: ItemStack = ItemStack(Material.COMPASS)
    val dispenser: ItemStack = ItemStack(Material.DISPENSER)
    val itemFrame: ItemStack = ItemStack(Material.ITEM_FRAME)

    val btn1v1: ItemStack get () {
            val diamondAxe = ItemStack(Material.GOLDEN_APPLE)
            val meta = diamondAxe.itemMeta
            meta.displayName = "1對1"
            diamondAxe.setItemMeta(meta)
            return diamondAxe
        }

    val compassItemMeta: ItemMeta = compass.itemMeta
    val dispenserItemMeta: ItemMeta = dispenser.itemMeta
    val itemFrameItemMeta: ItemMeta = itemFrame.itemMeta

    val ironHelmet: ItemStack = ItemStack(Material.IRON_HELMET)
    val ironChestplate: ItemStack = ItemStack(Material.IRON_CHESTPLATE)
    val ironLeggings: ItemStack = ItemStack(Material.IRON_LEGGINGS)
    val ironBoots: ItemStack = ItemStack(Material.IRON_BOOTS)

    val diamondHelmet: ItemStack = ItemStack(Material.DIAMOND_HELMET)
    val diamondChestplate: ItemStack = ItemStack(Material.DIAMOND_CHESTPLATE)
    val diamondLeggings: ItemStack = ItemStack(Material.DIAMOND_LEGGINGS)
    val diamondBoots: ItemStack = ItemStack(Material.DIAMOND_BOOTS)

//    val leatherHelmet: ItemStack = ItemStack(Material.LEATHER_HELMET)
//    val leatherChestplate: ItemStack = ItemStack(Material.LEATHER_CHESTPLATE)
//    val leatherLeggings: ItemStack = ItemStack(Material.LEATHER_LEGGINGS)
//    val leatherBoots: ItemStack = ItemStack(Material.LEATHER_BOOTS)

    val fishingRod: ItemStack = ItemStack(Material.FISHING_ROD)
    val gapple: ItemStack = ItemStack(Material.GOLDEN_APPLE, 5)
    val steak: ItemStack = ItemStack(Material.COOKED_BEEF, 64)

    val exit: ItemStack get () {
        val exit = ItemStack(Material.REDSTONE)
        val meta = exit.itemMeta
        meta.displayName = "退出"
        exit.setItemMeta(meta)
        return exit
    }

    fun createHead(player: Player): ItemStack {
        val item = ItemStack(Material.SKULL_ITEM, 1, 0, 3)
        val meta = item.itemMeta as SkullMeta
        meta.owner = player.displayName
        item.itemMeta = meta
        return item
    }
}
