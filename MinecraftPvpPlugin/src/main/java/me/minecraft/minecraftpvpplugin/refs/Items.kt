package me.minecraft.minecraftpvpplugin.refs

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta


        object Items {
    val ironSword = ItemStack(Material.IRON_SWORD)

    val ironHelmet = ItemStack(Material.IRON_HELMET)
    val ironChestplate = ItemStack(Material.IRON_CHESTPLATE)
    val ironLeggings = ItemStack(Material.IRON_LEGGINGS)
    val ironBoots = ItemStack(Material.IRON_BOOTS)

    val diamondHelmet = ItemStack(Material.DIAMOND_HELMET)
    val diamondChestplate = ItemStack(Material.DIAMOND_CHESTPLATE)
    val diamondLeggings = ItemStack(Material.DIAMOND_LEGGINGS)
    val diamondBoots = ItemStack(Material.DIAMOND_BOOTS)

//    val leatherHelmet: ItemStack = ItemStack(Material.LEATHER_HELMET)
//    val leatherChestplate: ItemStack = ItemStack(Material.LEATHER_CHESTPLATE)
//    val leatherLeggings: ItemStack = ItemStack(Material.LEATHER_LEGGINGS)
//    val leatherBoots: ItemStack = ItemStack(Material.LEATHER_BOOTS)

    val fishingRod = ItemStack(Material.FISHING_ROD)
    val gapple = ItemStack(Material.GOLDEN_APPLE, 5)
    val steak = ItemStack(Material.COOKED_BEEF, 64)

    val compass = ItemStack(Material.COMPASS)
    val dispenser = ItemStack(Material.DISPENSER)
    val itemFrame = ItemStack(Material.ITEM_FRAME)

    val exit = namedItemStackGUI(ItemStack(Material.REDSTONE), "退出")
    val btn1v1 = namedItemStackGUI(ItemStack(Material.REDSTONE), "1對1")

    val join = namedItemStackGUI(compass, "Join")
    val editKit = namedItemStackGUI(dispenser, "Edit Kit")
    val leaderBoard = namedItemStackGUI(itemFrame, "Leader Board")

    fun createHead(player: Player, name: String = player.displayName): ItemStack {
        val item = ItemStack(Material.SKULL_ITEM, 1, 0, 3)
        val meta = item.itemMeta as SkullMeta
        meta.owner = player.displayName
        meta.displayName = name
        item.itemMeta = meta
        return item
    }

    fun namedItemStackGUI(itemStack: ItemStack, name: String, unbreakable: Boolean = itemStack.type.maxDurability > 0): ItemStack {
        val clone = itemStack.clone()
        val meta = clone.itemMeta
        meta.displayName = name
        meta.spigot().isUnbreakable = unbreakable
        clone.setItemMeta(meta)
        return clone
    }
}
