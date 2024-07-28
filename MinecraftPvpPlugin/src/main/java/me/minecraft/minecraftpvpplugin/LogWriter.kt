package me.minecraft.minecraftpvpplugin

import java.io.File
import java.io.FileWriter

object LogWriter {
    fun LogWriter(text: String){
        val file = File("Skyjet/log/log.txt")
        val writer = FileWriter(file, true)
        writer.write(text)
        writer.close()
    }
}