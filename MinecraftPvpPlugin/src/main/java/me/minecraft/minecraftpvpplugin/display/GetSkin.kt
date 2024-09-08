package me.minecraft.minecraftpvpplugin.display

import org.json.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


object GetSkin {
    fun getSkinTexture(uuid: UUID): String? {
        try {
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/${uuid.toString().replace("-", "")}"
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")

            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            val response = reader.readText()
            reader.close()

            val jsonObject = JSONObject(response)

            val properties = jsonObject.get("properties") as JSONArray
            for (property in properties) {
                val propertyObj = property as JSONObject
                if ("textures" == propertyObj.get("name")) {
                    return propertyObj.get("value") as String
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getSkinSignature(uuid: UUID): String? {
        try {
            val url = "https://sessionserver.mojang.com/session/minecraft/profile/${uuid.toString().replace("-", "")}?unsigned=false"

            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")

            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            val response = reader.readText()
            reader.close()

            val jsonObject = JSONObject(response)

            val properties = jsonObject.get("properties") as JSONArray
            for (property in properties) {
                val propertyObj = property as JSONObject
                if ("textures" == propertyObj.get("name")) {
                    return propertyObj.get("signature") as String
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}