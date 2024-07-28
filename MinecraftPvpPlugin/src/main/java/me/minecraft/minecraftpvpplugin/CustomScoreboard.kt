package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class CustomScoreboard(private val objectiveNameBoardFormatMap: HashMap<String, String>) {

    // Player Name -> Field Name -> Value
    private var playerNameBoardDataMap: HashMap<String, HashMap<String, String>> = hashMapOf()

    private val manager = Bukkit.getScoreboardManager()

    fun load() {
        val file = File("Skyjet/scoreboard/custom-scoreboard.json")

        if (file.exists()) {
            val reader = FileReader(file)
            playerNameBoardDataMap = JSONObject(reader.readText()).toMap() as HashMap<String, HashMap<String, String>>
        }
    }

    fun save() {
        val file = File("Skyjet/scoreboard/custom-scoreboard.json")
        val writer = FileWriter(file)
        JSONObject(playerNameBoardDataMap).write(writer)
        writer.close()
    }

    fun initScoreboard(player: Player, fieldNameValueMap: HashMap<String, String>) {
        playerNameBoardDataMap[player.name] = fieldNameValueMap
        updateScoreboard(player)
    }

    fun changeScoreboard(player: Player, fieldName: String, value: String) {
        playerNameBoardDataMap[player.name]!![fieldName] = value
        updateScoreboard(player)
    }

    fun getScoreboard(player: Player, fieldName: String): String {
        return playerNameBoardDataMap[player.name]!![fieldName]!!
    }

    fun increaseScoreboardInt(player: Player, fieldName: String, amount: Int = 1) {
        changeScoreboard(player, fieldName, (getScoreboard(player, fieldName).toInt() + amount).toString())
    }

    fun havePlayerData(player: Player): Boolean {
        return playerNameBoardDataMap.containsKey(player.name)
    }

    private fun updateScoreboard(player: Player) {
        val board = manager.newScoreboard
        val fieldNameValueMap = playerNameBoardDataMap[player.name]!!

        for ((objectiveName, boardFormat) in objectiveNameBoardFormatMap) {
            val objective = board.registerNewObjective(objectiveName, "dummy")

            objective.displayName = objectiveName
            objective.displaySlot = DisplaySlot.SIDEBAR

            val matchResults = Regex("\\{([A-Za-z]*)}").findAll(boardFormat)

            var formattedBoard = boardFormat

            for (matchResult in matchResults) {
                val entireMatch = matchResult.groupValues[0]
                val fieldName = matchResult.groupValues[1]

                formattedBoard = formattedBoard.replace(entireMatch, fieldNameValueMap[fieldName]!!)
            }

            var index = 0

            for (line in formattedBoard.lines().reversed()) {
                print(line)
                objective.getScore(line).score = index
                index += 1
            }
        }

        player.scoreboard = board
    }
}