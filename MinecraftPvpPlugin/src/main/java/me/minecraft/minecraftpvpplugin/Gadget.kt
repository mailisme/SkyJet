package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.RunAfter
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

// Duration will only do any effect when SwitchLike = false

// Switch Like:
//     false: player click -> activate() -> delete item -> wait for `duration` s  -> deactivate()
//     true: player click -> activate() -> player click -> deactivate() -> delete item

abstract class Gadget(
    val material: Material,
    private val name: String,
    private val switchLike: Boolean = false,
    private val duration: Long? = null
) : ItemStack(), Listener {
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

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }

    fun addPlayerData(player: Player, key: String, data: Any) {
        playersUsingGadgetData[player]?.set(key, data)
    }

    fun getPlayerData(player: Player, key: String): Any? {
        return playersUsingGadgetData[player]?.get(key)
    }

    fun isPlayerUsingGadget(player: Player): Boolean {
        return playersUsingGadgetData.contains(player)
    }

    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (Worlds.isInPvp(player) && item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL) {
            if (switchLike) {
                if (isPlayerUsingGadget(player)) {
                    removeOneItemInHand(player)
                    onDeactivate(event)
                    playersUsingGadgetData.remove(player)
                }
            } else {
                if (!isPlayerUsingGadget(player)) {
                    removeOneItemInHand(player)
                    waitToDeactivate(player, event)
                }
            }

            if (!isPlayerUsingGadget(player)) {
                playersUsingGadgetData[player] = mutableMapOf()
                onActivate(event)
            }
        }
    }

    @EventHandler
    fun handleChangeWorld(event: PlayerChangedWorldEvent) {
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

    private fun waitToDeactivate(player: Player, event: PlayerInteractEvent) {
        RunAfter(duration!!) {
            if (playersUsingGadgetData.contains(player)) {
                onDeactivate(event)
                playersUsingGadgetData.remove(player)
            }
        }
    }

    fun create(amount: Int = 1): ItemStack {
        val stack = this.clone()
        stack.amount = amount
        return stack
    }
}
