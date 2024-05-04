package me.minecraft.minecraftpvpplugin.refs

import me.minecraft.minecraftpvpplugin.Skill
import me.minecraft.minecraftpvpplugin.skills.*

object Skills {
    val instantHeal = InstantHeal
    val allDie = AllDie

    val skills = arrayOf<Skill>(instantHeal, allDie)
}