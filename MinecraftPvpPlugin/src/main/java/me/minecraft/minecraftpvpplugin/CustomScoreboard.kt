package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class CustomScoreboard(private val objectiveNameBoardFormatMap: HashMap<String, String>, private val displaySlot: DisplaySlot) {
    private val manager = Bukkit.getScoreboardManager()

    fun updateScoreboard(player: Player) {
        if (player.scoreboard == manager.mainScoreboard) {
            player.scoreboard = manager.newScoreboard
        }

        player.scoreboard.getObjective(displaySlot.name)?.unregister()

        for ((objectiveFormat, boardFormat) in objectiveNameBoardFormatMap) {
            val objective = player.scoreboard.registerNewObjective(displaySlot.name, "dummy")

            objective.displayName = format(objectiveFormat, player)
            objective.displaySlot = displaySlot

            var index = 0

            for (line in format(boardFormat, player).split('\n').reversed()) {
                objective.getScore(line).score = index
                index += 1
            }
        }
    }

    private fun format(formatter: String, player: Player): String {
        val matchResults = Regex("\\{([A-Za-z]*)}").findAll(formatter)

        var formatted = formatter

        for (matchResult in matchResults) {
            val entireMatch = matchResult.groupValues[0]
            val fieldName = matchResult.groupValues[1]

            formatted = formatted.replace(entireMatch, DataManager.get(player, fieldName))
        }

        return formatted
    }
}