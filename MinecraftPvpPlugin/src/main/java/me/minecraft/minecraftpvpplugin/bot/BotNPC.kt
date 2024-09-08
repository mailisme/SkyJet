package me.minecraft.minecraftpvpplugin.bot

import com.mojang.authlib.GameProfile
import net.minecraft.server.v1_8_R3.*
import net.minecraft.server.v1_8_R3.PacketPlayInFlying.PacketPlayInPositionLook
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

class BotNPC(
    location: Location,
    private val player: Player,
    private val name: String,
    private val craftPlayer: CraftPlayer = player as CraftPlayer,
    private val entityPlayer: EntityPlayer = craftPlayer.handle,
    private val ps: PlayerConnection = entityPlayer.playerConnection
) : EntityPlayer(entityPlayer.server, (player.world as CraftWorld).handle, GameProfile(UUID.randomUUID(), name), entityPlayer.playerInteractManager) {
    init {
        setLocation(
            location.x,
            location.y,
            location.z,
            location.yaw,
            location.pitch
        )

        ps.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this))
        ps.sendPacket(PacketPlayOutNamedEntitySpawn(this))
        ps.sendPacket(PacketPlayOutEntityEquipment(getBukkitEntity().entityId, 0, ItemStack(Items.IRON_SWORD)))
    }

//    fun updatePos(to: Location) {
//        val deltaPos = to.clone().add(-locX, -locY, -locZ)
//
//        // Move in advance so onGround can be updated
//        move(deltaPos.x, deltaPos.y, deltaPos.z)
//
//        ps.sendPacket(
//            PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(
//                bukkitEntity.entityId,
//                (deltaPos.x * 1.shl(5)).toInt().toByte(),
//                (deltaPos.y * 1.shl(5)).toInt().toByte(),
//                (deltaPos.z * 1.shl(5)).toInt().toByte(),
//                (to.yaw / 365.0 * 256.0).toInt().toByte(),
//                (to.pitch / 365.0 * 256.0).toInt().toByte(),
//                onGround
//            )
//        )
//    }
}