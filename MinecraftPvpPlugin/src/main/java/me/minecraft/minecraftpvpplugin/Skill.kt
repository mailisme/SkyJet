package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.roundToLong


abstract class Skill(
    val material: Material,
    val name: String,
    private val coolDownSeconds: Double = 0.0,
    private val ignoreCoolDownSeconds: Double = 0.0,
    private val switchLike: Boolean = false,
) : ItemStack(), Listener {

    init {
        this.type = material
        this.amount = 1

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }


    private var coolDownFinishTimestampMap: MutableMap<Player, Long> = HashMap()

    private var playersActivating = mutableListOf<Player>()

    private val coolDownMilliSeconds = (coolDownSeconds * 1000).roundToLong()

    private val ignoreCoolDownMilliSeconds = (ignoreCoolDownSeconds * 1000).roundToLong()

    @EventHandler
    fun handleChangeWorld(event: PlayerChangedWorldEvent) {
        if (event.player.world === Bukkit.getWorld("Lobby")) {
            playersActivating.remove(event.player)
        }
    }

    fun isTriggerActivateSuccessful(player: Player): Boolean {
        if (!hasSkill(player)) return false
        if (isActivating(player)) return false

        val currTimestamp = System.currentTimeMillis()

        if (coolDownFinishTimestampMap.containsKey(player)) {
            print(coolDownFinishTimestampMap[player])

            if (currTimestamp <= coolDownFinishTimestampMap[player]!! &&
                currTimestamp >= coolDownFinishTimestampMap[player]!! - (coolDownMilliSeconds - ignoreCoolDownMilliSeconds)
            ) {
                val leftTime = coolDownFinishTimestampMap[player]!! - currTimestamp
                print(leftTime)
                player.sendMessage(String.format("再等 %.1f 秒", leftTime / 1000.0))
                return false
            }
        } else {
            coolDownFinishTimestampMap[player] = currTimestamp + coolDownMilliSeconds
        }

        if (currTimestamp >= coolDownFinishTimestampMap[player]!! - (coolDownMilliSeconds - ignoreCoolDownMilliSeconds)) {
            coolDownFinishTimestampMap[player] = currTimestamp + coolDownMilliSeconds
        }

        if (switchLike) playersActivating.add(player)
        return true
    }

    fun isTriggerDeactivateSuccessful(player: Player): Boolean {
        if (!hasSkill(player)) return false
        if (!switchLike) return false
        if (!isActivating(player)) return false

        playersActivating.remove(player)
        return true
    }

    fun isClickEventClickingItself(event: PlayerInteractEvent): Boolean {
        return event.item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL
    }

    fun hasSkill(player: Player): Boolean {
        return Worlds.isInPvp(player) && PvpPlaceManager.getPlayerSkill(player) == this
    }

    fun isActivating(player: Player): Boolean {
        return playersActivating.contains(player)
    }
}