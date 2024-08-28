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

// Duration will only take an effect when SwitchLike = false

// Switch Like:
//     false: player click -> activate() -> delete item -> wait for `duration` s  -> deactivate()
//     true: player click -> activate() -> player click -> deactivate() -> delete item

abstract class Gadget(
    val material: Material,
    private val name: String,
                      private val lore: List<String> = listOf(),
                      private val duration: Double? = null,
                      private val switchLike: Boolean = false,
    ) : ItemStack(), Listener {

        // Can be used to store information about individual players that is activating this gadget
        private var playersActivatingData = mutableMapOf<Player, MutableMap<String, Any>>()

        open fun onActivate(event: PlayerInteractEvent) {}
        open fun onDeactivate(event: PlayerInteractEvent) {}
        open fun onGameEnd(event: PlayerChangedWorldEvent) {}

        init {
            this.type = material
            this.amount = 1

            val meta = this.itemMeta
            meta.displayName = name
            meta.lore = lore
            this.setItemMeta(meta)

            if (!switchLike && duration == null) {
                throw IllegalArgumentException("Duration must be specified if switchLike = false")
            }

            Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
        }

        fun addPlayerData(player: Player, key: String, data: Any) {
        playersActivatingData[player]?.set(key, data)
    }

    fun getPlayerData(player: Player, key: String): Any? {
        return playersActivatingData[player]?.get(key)
    }

    fun isActivating(player: Player): Boolean {
        return playersActivatingData.contains(player)
    }

    @EventHandler
    fun handleClick(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        // TODO: Make if nest more readable

        if (Worlds.isInPvp(player) && item?.itemMeta?.displayName == name && event.action != Action.PHYSICAL) {
            if (switchLike) {
                if (isActivating(player)) {
                    removeOneItemInHand(player)
                    onDeactivate(event)
                    playersActivatingData.remove(player)
                }
            } else {
                if (!isActivating(player)) {
                    removeOneItemInHand(player)
                    waitToDeactivate(player, event)
                }
            }

            if (!isActivating(player)) {
                playersActivatingData[player] = mutableMapOf()
                onActivate(event)
            }
        }
    }

    @EventHandler
    fun handleChangeWorld(event: PlayerChangedWorldEvent) {
        if (event.player.world === Bukkit.getWorld("Lobby")) {
            onGameEnd(event)
            playersActivatingData.remove(event.player)
        }
    }

    private fun removeOneItemInHand(player: Player) {
        val item = player.itemInHand

        if (item.amount == 1) {
            player.inventory.itemInHand = null
        } else {
            item.amount -= 1
        }
    }

    private fun waitToDeactivate(player: Player, event: PlayerInteractEvent) {
        RunAfter(duration!!) {
            if (playersActivatingData.contains(player)) {
                onDeactivate(event)
                playersActivatingData.remove(player)
            }
        }
    }
}
