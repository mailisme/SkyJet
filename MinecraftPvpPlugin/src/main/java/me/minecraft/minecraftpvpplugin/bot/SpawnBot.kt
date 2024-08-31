package me.minecraft.minecraftpvpplugin.bot

import com.mojang.authlib.GameProfile
import me.minecraft.minecraftpvpplugin.refs.Items
import net.minecraft.server.v1_8_R3.*
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import java.util.*

object SpawnBot {
    fun bot(player: Player) {

        val craftPlayer: CraftPlayer = player as CraftPlayer
        val entityPlayer: EntityPlayer = craftPlayer.handle

        val minecraftServer: MinecraftServer = entityPlayer.server
        val worldServer: WorldServer = minecraftServer.getWorldServer(1)

        val gameProfile = GameProfile(UUID.randomUUID(), "BOT")
        val pim: PlayerInteractManager = entityPlayer.playerInteractManager

        val bot = EntityPlayer(minecraftServer, worldServer, gameProfile, pim)
        bot.setLocation(craftPlayer.location.x, craftPlayer.location.y, craftPlayer.location.z, craftPlayer.location.yaw, craftPlayer.location.pitch)

        val ps = entityPlayer.playerConnection

        ps.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, bot))
        ps.sendPacket(PacketPlayOutNamedEntitySpawn(bot))
        ps.sendPacket(PacketPlayOutEntityEquipment(bot.bukkitEntity.entityId, 1, ItemStack(CraftMagicNumbers.getItem(Material.IRON_SWORD))))


    }
}