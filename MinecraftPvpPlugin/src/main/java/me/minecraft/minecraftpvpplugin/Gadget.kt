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

abstract class Gadget : ItemStack, Listener {
    var material: Material
    var name: String
    var duration: Long = 0 // this will only do any effect when SwitchLike = false

    // Switch Like:
    //     false: player click -> activate() -> delete item -> wait for `duration` s  -> deactivate()
    //     true: player click -> activate() -> player click -> deactivate() -> delete item
    var SwitchLike: Boolean

    var PlayersUsingGadget: MutableList<Player> = ArrayList()
    protected open fun onActivate(event: PlayerInteractEvent) {
    }

    protected open fun onDeactivate(event: PlayerInteractEvent) {
    }

    protected open fun onGameEnd(event: PlayerChangedWorldEvent) {
    }


    constructor(material: Material, name: String, SwitchLike: Boolean) {
        this.material = material
        this.name = name
        this.SwitchLike = SwitchLike

        this.type = material

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        Bukkit.getServer().pluginManager.registerEvents(this, MinecraftPvpPlugin.Companion.instance)
    }

    constructor(material: Material, name: String, duration: Long) {
        this.material = material
        this.name = name
        this.duration = duration
        this.SwitchLike = false

        this.type = material

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        println(Bukkit.getServer().pluginManager)
        println(Bukkit.getServer().pluginManager.getPlugin("MinecraftPvpPlugin"))

        Bukkit.getServer().pluginManager.registerEvents(this, MinecraftPvpPlugin.Companion.instance)
    }

    @EventHandler
    fun Click(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (event.item != null && MinecraftPvpPlugin.Companion.IsInPvp(player) && item.itemMeta.displayName == name && event.action != Action.PHYSICAL
        ) {
            if (SwitchLike) {
                if (PlayersUsingGadget.contains(player)) {
                    RemoveOneItemInHand(player)
                    PlayersUsingGadget.remove(player)
                    onDeactivate(event)
                } else {
                    PlayersUsingGadget.add(player)
                    onActivate(event)
                }
            } else {
                if (!PlayersUsingGadget.contains(player)) {
                    RemoveOneItemInHand(player)
                    PlayersUsingGadget.add(player)
                    onActivate(event)

                    Timer().schedule(
                            object : TimerTask() {
                                override fun run() {
                                    if (PlayersUsingGadget.contains(player)) {
                                        PlayersUsingGadget.remove(player)
                                        onDeactivate(event)
                                    }
                                }
                            },
                            duration * 1000
                    )
                }
            }
        }
    }

    @EventHandler
    fun ChangeWorld(event: PlayerChangedWorldEvent) {
        if (event.player.world === Bukkit.getWorld("Lobby")) {
            PlayersUsingGadget.remove(event.player)
            onGameEnd(event)
        }
    }

    fun RemoveOneItemInHand(player: Player) {
        val item = player.itemInHand

        if (item.amount == 1) {
            player.inventory.itemInHand = null
        } else {
            player.inventory.itemInHand = instance(item.amount - 1)
        }
    }

    fun instance(amount: Int): ItemStack {
        val stack = this.clone()
        stack.amount = amount
        return stack
    }

    fun instance(): ItemStack {
        val stack = this.clone()
        stack.amount = 1
        return stack
    }
}
