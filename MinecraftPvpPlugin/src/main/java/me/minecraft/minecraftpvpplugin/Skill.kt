package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.time.Instant

open class Skill(val material: Material, val name: String, private val coolDownSeconds: Long) : ItemStack(), Listener {
    private var coolDownFinishTimestampMap: MutableMap<Player, Long> = HashMap()
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
        val currTimestamp = Instant.now().epochSecond


        if (Worlds.isInPvp(player) && item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL) {
            if (coolDownFinishTimestampMap.containsKey(player)) {
                if (currTimestamp > coolDownFinishTimestampMap[player]!!) {
                    coolDownFinishTimestampMap[player] = currTimestamp + coolDownSeconds
                    onClick(event)
                } else {
                    player.sendMessage("再等" + (coolDownFinishTimestampMap[player]!! - System.currentTimeMillis() / 1000) + "秒")
                }
            }

            else {
                coolDownFinishTimestampMap[player] = currTimestamp + coolDownSeconds
                onClick(event)
            }

//            coolDownFinishTimestampMap[player]?.let {
//                if (currTimestamp > it) {
//                    coolDownFinishTimestampMap[player] = currTimestamp + coolDownSeconds
//                    onClick(event)
//                } else {
//                    player.sendMessage("再等" + (it - System.currentTimeMillis() / 1000) + "秒")
//                }
//            }
        }
    }
}