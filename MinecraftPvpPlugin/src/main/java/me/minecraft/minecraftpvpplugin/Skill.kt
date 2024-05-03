package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

open class Skill(val material: Material?, val name: String?) : ItemStack(), Listener {
//    var DelayMap: MutableMap<Player, Long> = HashMap()
//    var delay: Long? = null

    protected open fun onClick(event: PlayerInteractEvent) {}
    init {
        this.type = material
        this.amount = 1

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }

    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (Worlds.isInPvp(player) && item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL) {
            onClick(event)
        }
    }

//    @EventHandler
//    fun Click(event: PlayerInteractEvent) {
//        val time = Instant.now().epochSecond
//        val player = event.player as Player
//        if (event.item == null) {
//            if (event.action != Action.PHYSICAL) {
//                if (DelayMap.containsKey(player)) {
//                    if (time - DelayMap[player]!! > delay!!) {
//                        DelayMap[player] = time
//                        onActivate(event)
//                    }
//                } else {
//                    DelayMap[player] = time
//                    onActivate(event)
//                }
//            }
//        }
//    }


}