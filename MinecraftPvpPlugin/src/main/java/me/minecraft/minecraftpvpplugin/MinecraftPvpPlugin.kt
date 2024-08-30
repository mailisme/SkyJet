package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.bot.SpawnBot
import me.minecraft.minecraftpvpplugin.display.CustomScoreboard
import me.minecraft.minecraftpvpplugin.display.CustomTag
import me.minecraft.minecraftpvpplugin.helpers.RunEveryFor
import me.minecraft.minecraftpvpplugin.pvp_place.PvpPlaceManager
import me.minecraft.minecraftpvpplugin.refs.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import java.util.logging.Level

class MinecraftPvpPlugin : JavaPlugin(), Listener {
    override fun onEnable() {
        logger.level = Level.ALL

        // Plugin startup logic
        logger.info("SKYJET PVP v.3.0.0")
        server.pluginManager.registerEvents(this, this)

        Worlds.lobby.pvp = false

        Worlds.lobby.setGameRuleValue("doMobSpawning", "false")
        Worlds.lobby.setGameRuleValue("doDaylightCycle", "true")
        Worlds.lobby.setGameRuleValue("doWeatherCycle", "true")

        instance = this

        mainScoreboard = CustomScoreboard(
            hashMapOf(
                "${ChatColor.AQUA}${ChatColor.BOLD}SkyJet" to """
                ${ChatColor.RESET}${ChatColor.WHITE}================
                    
                        
                ${ChatColor.GRAY}Welcome ${ChatColor.GOLD}{name}!
                ${ChatColor.RED}Won {kill} times
                ${ChatColor.AQUA}You are in Level ${ChatColor.RED}{level}${'\n'}${'\n'}
                            
                ${ChatColor.WHITE}================
                """.trimIndent()
            ),
            DisplaySlot.SIDEBAR
        )

        customTag = CustomTag("{nameColor}{shortenedName}${ChatColor.RED}Lv{level}")

        DataManager.load()

        RunEveryFor (300.0) {
            DataManager.save()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val player = sender as Player
        if (command.name.equals("lobby", ignoreCase = true)) {
            if (Worlds.isInPvp(player)) PvpPlaceManager.removePlayer(player, "leave")
            onPlayerToLobby(player)
        }

        if (command.name.equals("start", ignoreCase = true) && player.isOp) {
            PvpPlaceManager.startGame(player.world)
        }

        if (command.name.equals("data", ignoreCase = true) && player.isOp) {
            if (args.size == 3 && args[0] == "get") {
                sender.sendMessage(DataManager.get(Bukkit.getOfflinePlayer(args[1]).uniqueId, args[2]))
            }

            if (args.size == 4 && args[0] == "set") {
                val offlinePlayer = Bukkit.getOfflinePlayer(args[1])
                val onlinePlayer = Bukkit.getPlayer(args[1])

                if(args[2] != "nameColor") {
                    DataManager.set(offlinePlayer.uniqueId, args[2], args[3])
                }
                else {
                    args[3] = when (args[3]) {
                        "owner" -> "&6"
                        "mod" -> "&4"
                        "builder" -> "&3"
                        "media" -> "&c"
                        else -> args[3]
                    }

                    DataManager.set(offlinePlayer.uniqueId, args[2], args[3])
                }

                val shortenedLength = 16 - 2 - 2 - DataManager.get(offlinePlayer.uniqueId, "level").length - DataManager.get(offlinePlayer.uniqueId, "nameColor").length
                DataManager.set(offlinePlayer.uniqueId, "shortenedName", DataManager.get(offlinePlayer.uniqueId, "name").take(shortenedLength))

                if (onlinePlayer != null) {
                    mainScoreboard.renderScoreboard(onlinePlayer)
                    customTag.updateTag(onlinePlayer)
                }
            }

            else sender.sendMessage("usage: /data set <player> <field> <value>" +
                                    "       /data get <player> <field>")
        }

        if (command.name.equals("bot", ignoreCase = true)) {
            SpawnBot.bot(player)
        }

        return true
    }

    override fun onDisable() {
        DataManager.save()
    }

    @EventHandler
    fun handleInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.clickedInventory == null) return

        if (event.clickedInventory.title.equals("${ChatColor.AQUA}加入遊戲", ignoreCase = true)) {
            when (event.currentItem.type) {
                Items.btn1v1.type -> {
                    val selectGui = Bukkit.createInventory(player, 9, "${ChatColor.AQUA}請選擇想擁有的技能")
                    selectGui.contents = Skills.skills
                    player.openInventory(selectGui)
                }

                else -> {}
            }

            event.isCancelled = true
        }

        if (event.clickedInventory.title.equals("${ChatColor.AQUA}請選擇想擁有的技能", ignoreCase = true)) {
            for (skill in Skills.skills) {
                if (skill.name == event.currentItem.itemMeta.displayName) {
                    PvpPlaceManager.addPlayer(player, skill)
                }
            }

            event.isCancelled = true
        }
    }

    @EventHandler
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player


        event.joinMessage = "${ChatColor.AQUA}Welcome ${player.name}!"
        logger.info("${player.name} joined the server")

        onPlayerToLobby(player)

        DataManager.initPlayer(player, hashMapOf("name" to player.name, "shortenedName" to "", "kill" to "0", "level" to "1", "nameColor" to ""))

        val shortenedLength = 16 - 2 - 2 - DataManager.get(player, "level").length - DataManager.get(player, "nameColor").length
        DataManager.set(player, "shortenedName", DataManager.get(player, "name").take(shortenedLength))


        mainScoreboard.renderScoreboard(player)
        customTag.updateTag(player)
    }

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val player = event.player

        if (Worlds.isInLobby(player)) {
            if (event.item == null) return
            if (event.item == Items.diamondSword && event.action != Action.PHYSICAL) {
                val startGui = Bukkit.createInventory(player, 9, "${ChatColor.AQUA}加入遊戲")
                val joinGameBtn = Items.btn1v1

                startGui.contents = arrayOf(joinGameBtn)

                player.openInventory(startGui)
            }
        }
    }

    @EventHandler
    fun handlePlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player

        if (Worlds.isInLobby(player)) {
            player.sendMessage("U cant drop any item ok?")
            event.itemDrop.remove()
            player.inventory.setItem(0, event.itemDrop.itemStack)
        }
    }

    @EventHandler
    fun handleDamage(event: EntityDamageEvent) {
        if (event.entity.type != EntityType.PLAYER) return

        val player = event.entity as Player

        if ((player.health - event.finalDamage) <= 0) {
            event.isCancelled = true
            player.health = 20.0

            PvpPlaceManager.removePlayer(player, "kill")
        }
    }

    @EventHandler
    fun handlePlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (Worlds.isInPvp(player)) {
            PvpPlaceManager.removePlayer(player, "leave")
        }
    }

    companion object {
        var instance: JavaPlugin? = null
        lateinit var mainScoreboard: CustomScoreboard
        lateinit var customTag: CustomTag

        fun onPlayerToLobby(player: Player) {
            player.teleport(Locations.lobbySpawn)
            player.inventory.clear()
            Items.swordItemMeta.displayName = "${ChatColor.AQUA}Join"
            Items.swordItemMeta.spigot().isUnbreakable = true
            Items.diamondSword.setItemMeta(Items.swordItemMeta)
            player.inventory.setItem(0, Items.diamondSword)
            player.inventory.helmet = null
            player.inventory.chestplate = null
            player.inventory.leggings = null
            player.inventory.boots = null
            player.health = 20.0
            player.foodLevel = 20
            clearEffects(player)
            player.gameMode = GameMode.ADVENTURE
        }

        fun onPlayerToPvp(player: Player, skillItem: ItemStack) {
            player.inventory.clear()
            player.inventory.setItem(0, Items.ironSword)
            player.inventory.setItem(1, Items.fishingRod)
            player.inventory.setItem(2, skillItem)
            player.inventory.setItem(7, Items.steak)
            player.inventory.setItem(8, Items.gapple)
            player.health = 20.0
            player.foodLevel = 20
            clearEffects(player)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.ironChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            player.gameMode = GameMode.ADVENTURE
        }

        private fun clearEffects(player: Player) {
            for (effect in player.activePotionEffects) {
                player.removePotionEffect(effect.type)
            }
        }
    }
}