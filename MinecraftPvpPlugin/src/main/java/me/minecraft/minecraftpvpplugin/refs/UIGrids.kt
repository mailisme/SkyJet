package me.minecraft.minecraftpvpplugin.refs

import me.minecraft.minecraftpvpplugin.skills.*
import org.bukkit.ChatColor

object UIGrids {
    val selectSkillTitle = "${ChatColor.AQUA}請選擇想擁有的技能"

    val selectSkill = arrayOf(
        null,null,null       ,null     ,null   ,null ,null                   ,null,null,
        null,null, InstantHeal, BestToRun, Creeper, Thief, TeleportToOpponent,null,null,
        null,null,null       ,null     ,null   ,null ,null                   ,null,null,
    )

    val joinGameTitle = "${ChatColor.AQUA}加入遊戲"

    val joinGame = arrayOf(
        null,null,null,null,null        ,null,null,null,null,
        null,null,null,null,Items.btn1v1,null,null,null,null,
        null,null,null,null,null        ,null,null,null,null
    )

    val leaderBoardTitle = "${ChatColor.AQUA}Leader Board"
}