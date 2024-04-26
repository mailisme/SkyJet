package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.time.Instant

open class Skill(//important
    var material: Material?, var name: String?
) : ItemStack(), Listener {
    var DelayMap: MutableMap<Player, Long> = HashMap()
    var delay: Long? = null


    init {
        this.type = material

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }


    protected open fun onActivate(event: PlayerInteractEvent) {
    }

    @EventHandler
    fun Click(event: PlayerInteractEvent) {
        val time = Instant.now().epochSecond
        val player = event.player as Player
        if (event.item == null) {
            if (event.action != Action.PHYSICAL) {
                if (DelayMap.containsKey(player)) {
                    if (time - DelayMap[player]!! > delay!!) {
                        DelayMap[player] = time
                        onActivate(event)
                    }
                } else {
                    DelayMap[player] = time
                    onActivate(event)
                }
            }
        }
    }
}