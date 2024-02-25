package me.minecraft.minecraftpvpplugin

import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class MinecraftPvpPlugin : JavaPlugin(), Listener {
    override fun onEnable() {
        instance = this

        // Plugin startup logic
        println("MAGIC PVP v.0.0.0")
        server.pluginManager.registerEvents(this, this)
        val lobbyWorld = Bukkit.createWorld(WorldCreator("Lobby"))

        for (i in 0..2) {
            val world = Bukkit.createWorld(WorldCreator(String.format("PVP%d", i)))
            world.pvp = true
            PVPWorlds.add(world)
        }

        if (lobbyWorld != null) {
            logger.info("Lobby world loaded successfully.")
        } else {
            logger.warning("Failed to load Lobby world.")
        }
        for (world in Bukkit.getWorlds()) {
            logger.info("Loaded world: " + world.name)
        }

        lobbyWorld!!.pvp = false
    }

    @EventHandler
    fun ClickEvent(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.clickedInventory != null) {
            if (event.clickedInventory.title.equals(ChatColor.AQUA.toString() + "Join Game", ignoreCase = true)) {
                when (event.currentItem.type) {
                    Material.DIAMOND_AXE -> {
                        PvpPlace.AddPlayer(player)
                        player.closeInventory()
                    }

                    else -> {}
                }
                event.isCancelled = true
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name.equals("lobby", ignoreCase = true)) {
            val player = sender as Player
            PvpPlace.RemovePlayer(player)
            ToLobby(player)
        }

        return true
    }

    @EventHandler
    fun Join(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage = ChatColor.AQUA.toString() + "Welcome " + player.name
        print(player.name + " join the server")
        player.teleport(Locations.lobby)
        ToLobby(player)
    }

    @EventHandler
    fun Click(event: PlayerInteractEvent) {
        val player = event.player
        if (player.world === Bukkit.getWorld("Lobby")) {
            val gui = Bukkit.createInventory(player, 9, ChatColor.AQUA.toString() + "Join Game")
            val StartGame = Items.DiamondPickaxe
            val menu = arrayOf(StartGame)
            gui.contents = menu

            if (event.item != null) {
                if (event.item == Items.DiamondSword) {
                    if (event.action != Action.PHYSICAL) {
                        player.openInventory(gui)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        if (Bukkit.getWorld("Lobby") === player.world) {
            player.sendMessage("U cant drop any item ok?")
            event.itemDrop.remove()
            player.inventory.setItem(0, event.itemDrop.itemStack)
        }
    }

    @EventHandler
    fun PlayerDeathEvent(event: PlayerDeathEvent) {
        val player = event.entity
        player.spigot().respawn()
        PvpPlace.RemovePlayer(player)
        event.keepInventory = true
    }

    @EventHandler
    fun PlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player
        if (PVPWorlds.contains(player.world)) {
            PvpPlace.RemovePlayer(player)
        }
    }

    companion object {
        var PVPWorlds: MutableList<World> = ArrayList()
        var instance: JavaPlugin? = null

        fun ToLobby(player: Player?) {
            if (player != null) {
                println("To Lobby " + player.name)
                player.inventory.clear()
                Items.SwordItemMeta.displayName = ChatColor.AQUA.toString() + "Join"
                Items.SwordItemMeta.spigot().isUnbreakable = true
                Items.DiamondSword.setItemMeta(Items.SwordItemMeta)
                player.inventory.setItem(0, Items.DiamondSword)
                player.inventory.helmet = null
                player.inventory.chestplate = null
                player.inventory.leggings = null
                player.inventory.boots = null
                player.health = 20.0
                player.foodLevel = 20
                ClearEffects(player)
                player.gameMode = GameMode.ADVENTURE
            }
        }

        fun ToPVP(player: Player?) {
            if (player != null) {
                player.inventory.clear()
                player.inventory.setItem(0, Items.IronSword)
                player.inventory.setItem(1, Items.FishingRod)

                player.inventory.setItem(8, Items.Gapple)
                player.health = 20.0
                player.foodLevel = 20
                ClearEffects(player)
                player.inventory.helmet = Items.IronHelmet
                player.inventory.chestplate = Items.IronChestplate
                player.inventory.leggings = Items.IronLeggings
                player.inventory.boots = Items.IronBoots
                player.gameMode = GameMode.ADVENTURE
            }
        }

        fun ClearEffects(player: Player) {
            for (effect in player.activePotionEffects) {
                player.removePotionEffect(effect.type)
            }
        }

        fun IsInPvp(player: Player): Boolean {
            return PVPWorlds.contains(player.world)
        }
    }
}