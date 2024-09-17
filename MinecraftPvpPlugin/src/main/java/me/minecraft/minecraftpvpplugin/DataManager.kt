package me.minecraft.minecraftpvpplugin

import org.bukkit.entity.Player
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.internal.Intrinsics.Kotlin

object DataManager {
    // Player UUID String -> Field Name -> Value
    private var playerUUIDDataMap: HashMap<String, HashMap<String, String>> = hashMapOf()

    fun load() {
        val file = File("Skyjet/custom_data/player.json")

        if (file.exists()) {
            val text = FileReader(file).readText()

            if (text != "") {
                playerUUIDDataMap = JSONObject(text).toMap() as HashMap<String, HashMap<String, String>>
            }
        }
    }

    fun save() {
        val file = File("Skyjet/custom_data/player.json")
        val writer = FileWriter(file)
        JSONObject(playerUUIDDataMap).write(writer)
        writer.close()
    }


    fun initPlayer(player: Player, defaultBoardDataMap: HashMap<String, String>) {
        if (!hasData(player)) {
            playerUUIDDataMap[player.uniqueId.toString()] = defaultBoardDataMap
            return
        }

        for ((field, value) in defaultBoardDataMap) {
            playerUUIDDataMap[player.uniqueId.toString()]!!.putIfAbsent(field, value)
        }
    }

    fun format(formatter: String, player: Player): String {
        val matchResults = Regex("\\{([A-Za-z]*)}").findAll(formatter)

        var formatted = formatter

        for (matchResult in matchResults) {
            val entireMatch = matchResult.groupValues[0]
            val fieldName = matchResult.groupValues[1]

            formatted = formatted.replace(entireMatch, get(player, fieldName))
        }

        return formatted
    }
    fun format(formatter: String, playerUUID: UUID): String {
        val matchResults = Regex("\\{([A-Za-z]*)}").findAll(formatter)

        var formatted = formatter

        for (matchResult in matchResults) {
            val entireMatch = matchResult.groupValues[0]
            val fieldName = matchResult.groupValues[1]

            formatted = formatted.replace(entireMatch, get(playerUUID, fieldName))
        }

        return formatted
    }

    fun get(player: Player, fieldName: String): String {
        return playerUUIDDataMap[player.uniqueId.toString()]!![fieldName]!!
    }
    fun get(playerUUID: UUID, fieldName: String): String {
        return playerUUIDDataMap[playerUUID.toString()]!![fieldName]!!
    }

    fun set(player: Player, fieldName: String, value: String) {
        playerUUIDDataMap[player.uniqueId.toString()]!![fieldName] = value
    }
    fun set(playerUUID: UUID, fieldName: String, value: String) {
        playerUUIDDataMap[playerUUID.toString()]!![fieldName] = value
    }

    fun addInt(player: Player, fieldName: String, amount: Int = 1) {
        set(player, fieldName, (get(player, fieldName).toInt() + amount).toString())
    }
    fun addInt(playerUUID: UUID, fieldName: String, amount: Int = 1) {
        set(playerUUID, fieldName, (get(playerUUID, fieldName).toInt() + amount).toString())
    }

    fun getAllPlayerUUID(): List<UUID> {
        return playerUUIDDataMap.keys.map {
            UUID.fromString(it)
        }
    }

    fun getLeaderBoard(): List<Pair<UUID, Int>> {
        val level: HashMap<UUID, Int> = hashMapOf()
        for (i in getAllPlayerUUID()){
            val lvl = get(i, "kill")
            level.put(i, lvl.toInt())
        }
        var lvlList = level.toList()
        lvlList = lvlList.sortedWith(compareBy(
            { it.second },
            { it.first }
        )).reversed()
        return lvlList
    }


    private fun hasData(player: Player): Boolean {
        return playerUUIDDataMap.containsKey(player.uniqueId.toString())
    }
    private fun hasData(playerUUID: UUID): Boolean {
        return playerUUIDDataMap.containsKey(playerUUID.toString())
    }
}