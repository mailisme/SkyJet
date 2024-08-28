package me.minecraft.minecraftpvpplugin.display

import me.minecraft.minecraftpvpplugin.DataManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class CustomScoreboard(private val objectiveNameBoardFormatMap: HashMap<String, String>, private val displaySlot: DisplaySlot) {
    private val manager = Bukkit.getScoreboardManager()

    fun renderScoreboard(player: Player) {
        if (player.scoreboard == manager.mainScoreboard) {
            player.scoreboard = manager.newScoreboard
        }

        player.scoreboard.getObjective(displaySlot.name)?.unregister()

        for ((objectiveFormat, boardFormat) in objectiveNameBoardFormatMap) {
            val objective = player.scoreboard.registerNewObjective(displaySlot.name, "dummy")

            val formattedObjective = DataManager.format(objectiveFormat, player)
            val formattedBoard = DataManager.format(boardFormat, player)

            objective.displayName = formattedObjective
            objective.displaySlot = displaySlot

            var index = 0

            for (line in formattedBoard.split('\n').reversed()) {
                objective.getScore(line).score = index
                index += 1
            }
        }
    }
}