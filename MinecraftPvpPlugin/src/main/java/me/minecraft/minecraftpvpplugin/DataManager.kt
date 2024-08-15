package me.minecraft.minecraftpvpplugin

import org.bukkit.entity.Player
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.collections.HashMap

object DataManager {
    // Player UUID String -> Field Name -> Value
    private var playerUUIDDataMap: HashMap<String, HashMap<String, String>> = hashMapOf()

    fun load() {
        val file = File("Skyjet/scoreboard/custom-scoreboard.json")

        if (file.exists()) {
            val reader = FileReader(file)
            playerUUIDDataMap = JSONObject(reader.readText()).toMap() as HashMap<String, HashMap<String, String>>
        }
    }

    fun save() {
        val file = File("Skyjet/scoreboard/custom-scoreboard.json")
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

    fun get(player: Player, fieldName: String): String {
        return playerUUIDDataMap[player.uniqueId.toString()]!![fieldName]!!
    }

    fun set(player: Player, fieldName: String, value: String) {
        playerUUIDDataMap[player.uniqueId.toString()]!![fieldName] = value
    }

    fun addInt(player: Player, fieldName: String, amount: Int = 1) {
        set(player, fieldName, (get(player, fieldName).toInt() + amount).toString())
    }

    fun hasData(player: Player): Boolean {
        return playerUUIDDataMap.containsKey(player.uniqueId.toString())
    }
}