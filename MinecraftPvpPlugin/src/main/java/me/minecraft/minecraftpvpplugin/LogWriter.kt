package me.minecraft.minecraftpvpplugin

import java.io.File
import java.io.FileWriter
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object LogWriter {
    fun log(text: String){
        val file = File("Skyjet/log/log.txt")
        val writer = FileWriter(file, true)

        val time = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.S")
            .withZone(ZoneOffset.of("+08:00"))
            .format(Instant.now())

        writer.write("$time $text\n")
        writer.close()
    }
}