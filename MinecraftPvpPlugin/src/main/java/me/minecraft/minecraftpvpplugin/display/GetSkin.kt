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

    val MOJANG_API_URL: String = "https://sessionserver.mojang.com/session/minecraft/profile/"

    fun getSkinTexture(uuid: UUID): String? {
        try {
            val url: String = MOJANG_API_URL + uuid.toString().replace("-", "")
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")

            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            val response = reader.readLine()
            reader.close()

            val parser = JSONParser()
            val obj = parser.parse(response)
            val jsonObject: JSONObject = obj as JSONObject

            val properties: JSONArray = jsonObject.get("properties") as JSONArray
            for (property in properties) {
                val propertyObj: JSONObject = property as JSONObject
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
            val url: String = MOJANG_API_URL + uuid.toString().replace("-", "")
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestMethod("GET")

            val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
            val response = reader.readLine()
            reader.close()

            val parser = JSONParser()
            val obj = parser.parse(response)
            val jsonObject: JSONObject = obj as JSONObject

            val properties: JSONArray = jsonObject.get("properties") as JSONArray
            for (property in properties) {
                val propertyObj: JSONObject = property as JSONObject
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