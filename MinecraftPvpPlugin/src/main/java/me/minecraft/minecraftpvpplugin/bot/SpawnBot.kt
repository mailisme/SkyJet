package me.minecraft.minecraftpvpplugin.bot

import com.mojang.authlib.GameProfile
import net.minecraft.server.v1_8_R3.*
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.PlayerInfoData
import org.bukkit.Server
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

object SpawnBot : JavaPlugin(), Listener {
    public fun bot(player: Player) {

        val craftPlayer: CraftPlayer = player as CraftPlayer
        val entityPlayer: EntityPlayer = craftPlayer.handle

        val minecraftServer: MinecraftServer = entityPlayer.server
        val worldServer: WorldServer = minecraftServer.getWorldServer(1)

        val gameProfile = GameProfile(UUID.randomUUID(), "BOT")
        val pim: PlayerInteractManager = entityPlayer.playerInteractManager

        val bot = EntityPlayer(minecraftServer, worldServer, gameProfile, pim)

        val ps = entityPlayer.playerConnection

        ps.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, bot))

        ps.sendPacket()
    }
}