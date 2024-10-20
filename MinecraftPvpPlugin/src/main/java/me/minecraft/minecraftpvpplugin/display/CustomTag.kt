package me.minecraft.minecraftpvpplugin.display

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import io.netty.channel.Channel
import me.minecraft.minecraftpvpplugin.DataManager
import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.tiny_protocol.Reflection
import me.minecraft.minecraftpvpplugin.tiny_protocol.TinyProtocol
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage
import org.bukkit.entity.Player
import java.util.*


class CustomTag(private val tagFormat: String) {
    private var playerUUIDTagMap = hashMapOf<UUID, String>()

    init {
        val actionType = Reflection.getField("{nms}.PacketPlayOutPlayerInfo", PacketPlayOutPlayerInfo.EnumPlayerInfoAction::class.java, 0)
        val playerInfo = Reflection.getField("{nms}.PacketPlayOutPlayerInfo", Object::class.java, 1)


        object : TinyProtocol(MinecraftPvpPlugin.instance) {
            override fun onPacketOutAsync(reciever: Player?, channel: Channel?, packet: Any): Any {
                // Set the players' tag if they are spawned

                if (actionType.hasField(packet)) {
                    if (actionType.get(packet) == PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER) {
                        val infos = (playerInfo.get(packet) as ArrayList<PacketPlayOutPlayerInfo.PlayerInfoData>)

                        for (i in 0..<infos.count()) {
                            val info = infos[i]
                            val uuid = info.a().id
                            if (!playerUUIDTagMap.containsKey(uuid)) continue

                            val tag = playerUUIDTagMap[uuid]
                            val gameProfile = GameProfile(uuid, tag)
                            gameProfile.properties.put("textures", Property("textures", GetSkin.getSkinSignature(uuid), GetSkin.getSkinTexture(uuid)))

                            infos[i] = PacketPlayOutPlayerInfo().PlayerInfoData(
                                gameProfile,
                                info.b(),
                                info.c(),
                                CraftChatMessage.fromString(tag)[0]
                            )
                        }

                        playerInfo.set(packet, infos)
                    }
                }

                return super.onPacketOutAsync(reciever, channel, packet)
            }
        }
    }

    fun updateTag(whoseTag: Player) {
        var formattedTag = DataManager.format(tagFormat, whoseTag)
        formattedTag = ChatColor.translateAlternateColorCodes('&', formattedTag)

        // Truncate the tag down to 16 characters
        formattedTag = formattedTag.take(16)

        playerUUIDTagMap[whoseTag.uniqueId] = formattedTag

        // Respawn player so display name can be set

        val playerHandle = (whoseTag as CraftPlayer).handle

        for (onlinePlayer in Bukkit.getOnlinePlayers() + whoseTag) {
            val connection = (onlinePlayer as CraftPlayer).handle.playerConnection

            connection.sendPacket(PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerHandle
            ))

            connection.sendPacket(PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, playerHandle
            ))

            if (onlinePlayer != whoseTag) {
                connection.sendPacket(PacketPlayOutEntityDestroy(playerHandle.id))
                connection.sendPacket(PacketPlayOutNamedEntitySpawn(playerHandle))
            }
        }
    }
}