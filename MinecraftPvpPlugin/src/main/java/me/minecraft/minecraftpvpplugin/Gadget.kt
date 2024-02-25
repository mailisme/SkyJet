package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.*

// Duration will only do any effect when SwitchLike = false

// Switch Like:
//     false: player click -> activate() -> delete item -> wait for `duration` s  -> deactivate()
//     true: player click -> activate() -> player click -> deactivate() -> delete item

abstract class Gadget(var material: Material,
                      private var name: String,
                      private var switchLike: Boolean = false,
                      private var duration: Long? = null) : ItemStack(), Listener {
    private var playersUsingGadgetData = mutableMapOf<Player, MutableMap<String, Any>>()
    protected open fun onActivate(event: PlayerInteractEvent) {}
    protected open fun onDeactivate(event: PlayerInteractEvent) {}
    protected open fun onGameEnd(event: PlayerChangedWorldEvent) {}


    init {
        this.type = material

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        if (!switchLike && duration == null) {
            throw IllegalArgumentException("Duration must be specified if switchLike = false")
        }

        Bukkit.getServer().pluginManager.registerEvents(this, MinecraftPvpPlugin.instance)
    }


    private fun waitToDeactivate(player: Player, event: PlayerInteractEvent) {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    if (playersUsingGadgetData.contains(player)) {
                        onDeactivate(event)
                        playersUsingGadgetData.remove(player)
                    }
                }
            },
            duration!! * 1000
        )
    }

    fun addPlayerData(player: Player, key: String, data: Any) {
        if (!playersUsingGadgetData.contains(player)) {
            throw RuntimeException("Cannot add player data to players not using gadget")
        }

        playersUsingGadgetData[player]!![key] = data
    }

    fun getPlayerData(player: Player, key: String): Any {
        if (!playersUsingGadgetData.contains(player)) {
            throw RuntimeException("Cannot get player data from players not using gadget")
        }

        if (!playersUsingGadgetData[player]!!.contains(key)) {
            throw RuntimeException("This key is not stored in this player")
        }

        return playersUsingGadgetData[player]!![key]!!
    }

    fun isPlayerUsingGadget(player: Player): Boolean {
        return playersUsingGadgetData.contains(player)
    }

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (MinecraftPvpPlugin.IsInPvp(player) && item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL) {
            if (switchLike) {
                if (isPlayerUsingGadget(player)) {
                    removeOneItemInHand(player)
                    onDeactivate(event);
                    playersUsingGadgetData.remove(player);
                }
            } else {
                if (!isPlayerUsingGadget(player)) {
                    removeOneItemInHand(player)
                    waitToDeactivate(player, event);
                }
            }

            if (!isPlayerUsingGadget(player)) {
                playersUsingGadgetData[player] = mutableMapOf()
                onActivate(event)
            }
        }
    }

    @EventHandler
    fun onChangeWorld(event: PlayerChangedWorldEvent) {
        if (event.player.world === Bukkit.getWorld("Lobby")) {
            onGameEnd(event)
            playersUsingGadgetData.remove(event.player)
        }
    }

    private fun removeOneItemInHand(player: Player) {
        val item = player.itemInHand

        if (item.amount == 1) {
            player.inventory.itemInHand = null
        } else {
            player.inventory.itemInHand = create(item.amount - 1)
        }
    }

    fun create(amount: Int): ItemStack {
        val stack = this.clone()
        stack.amount = amount
        return stack
    }

    fun create(): ItemStack {
        val stack = this.clone()
        stack.amount = 1
        return stack
    }
}
