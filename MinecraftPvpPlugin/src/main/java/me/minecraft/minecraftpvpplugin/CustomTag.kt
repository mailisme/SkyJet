package me.minecraft.minecraftpvpplugin

import com.mojang.authlib.GameProfile
import io.netty.channel.Channel
import me.minecraft.minecraftpvpplugin.tiny_protocol.Reflection
import me.minecraft.minecraftpvpplugin.tiny_protocol.TinyProtocol
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.PlayerInfoData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage
import org.bukkit.entity.Player
import java.util.*


class CustomTag(private val tagFormat: String) {
    private var playerUUIDTagMap = hashMapOf<UUID, String>()

    init {
//        val manager = ProtocolLibrary.getProtocolManager()
//
//        manager.addPacketListener(object: PacketAdapter(MinecraftPvpPlugin.instance, PacketType.Play.Server.PLAYER_INFO) {
//
//            override fun onPacketSending(event: PacketEvent?) {
//                println("")
//                val packet = event!!.packet
//
//                if (packet.playerInfoAction.read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
//                    println("BON")
//                    val packetInfoData = packet.playerInfoDataLists.read(0);
//
//                    val iterator = packetInfoData.listIterator()
//
//                    for (data in iterator) {
//                        if (data == null) continue
//                        if (DataManager.hasData(data.profile.uuid)) {
//                            val uuid = data.profile.uuid
//                            var formattedTag = DataManager.format(tagFormat, uuid)
//                            formattedTag = ChatColor.translateAlternateColorCodes('&', formattedTag).take(16)
//
//                            iterator.set(PlayerInfoData(
//                                WrappedGameProfile(uuid, formattedTag),
//                                data.latency,
//                                data.gameMode,
//                                WrappedChatComponent.fromLegacyText(formattedTag)
//                            ))
//                        }
//                    }
//
//                    packet.playerInfoDataLists.write(0, packetInfoData)
//                }
//            }
//        })

        val actionType = Reflection.getField("{nms}.PacketPlayOutPlayerInfo", EnumPlayerInfoAction::class.java, 0)
        val playerInfo = Reflection.getField("{nms}.PacketPlayOutPlayerInfo", Object::class.java, 1)


        object : TinyProtocol(MinecraftPvpPlugin.instance) {
//            override fun onPacketInAsync(sender: Player?, channel: Channel?, packet: Any): Any? {
//                if (actionType.hasField(packet)) {
//                    if (actionType.get(packet) == PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER /* Add Player */) {
//                        println("${playerInfo.get(packet).`class`}")
//                    }
//                }
//
//                return super.onPacketInAsync(sender, channel, packet)
//            }

            override fun onPacketOutAsync(reciever: Player?, channel: Channel?, packet: Any): Any {
                if (actionType.hasField(packet)) {
                    if (actionType.get(packet) == EnumPlayerInfoAction.ADD_PLAYER /* Add Player */) {
                        val infos = (playerInfo.get(packet) as ArrayList<PlayerInfoData>)

                        for (i in 0..<infos.count()) {
                            val info = infos[i]
                            val uuid = info.a().id
                            if (!playerUUIDTagMap.containsKey(uuid)) continue

                            val tag = playerUUIDTagMap[uuid]

                            infos[i] = PacketPlayOutPlayerInfo().PlayerInfoData(
                                GameProfile(uuid, tag),
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
        formattedTag = ChatColor.translateAlternateColorCodes('&', formattedTag).take(16)

        playerUUIDTagMap[whoseTag.uniqueId] = formattedTag

        // Respawn player so display name can be set

        val playerHandle = (whoseTag as CraftPlayer).handle

        for (onlinePlayer in Bukkit.getOnlinePlayers() + whoseTag) {
            val connection = (onlinePlayer as CraftPlayer).handle.playerConnection

            connection.sendPacket(PacketPlayOutPlayerInfo(
                EnumPlayerInfoAction.REMOVE_PLAYER, playerHandle
            ))

            connection.sendPacket(PacketPlayOutPlayerInfo(
                EnumPlayerInfoAction.ADD_PLAYER, playerHandle
            ))

            if (onlinePlayer != whoseTag) {
                connection.sendPacket(PacketPlayOutEntityDestroy(playerHandle.id))
                connection.sendPacket(PacketPlayOutNamedEntitySpawn(playerHandle))
            }
        }
    }
}