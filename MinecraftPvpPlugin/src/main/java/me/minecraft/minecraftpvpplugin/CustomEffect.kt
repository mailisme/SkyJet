package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.helpers.RandomPointInSphere
import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.util.Vector

object CustomEffect {
    fun playParticleInSphere(location: Location, effect: Effect, amount: Int, spawnRadius: Float, data: Any? = null, viewRadius: Int = Int.MAX_VALUE) {
        repeat(amount) {
            val loc = location.clone()
            loc.world.playEffect(
                loc
                    .add(RandomPointInSphere.generate(spawnRadius))
                , effect, data, viewRadius
            )
        }
    }

    fun playParticleInSphere(player: Player, effect: Effect, amount: Int, spawnRadius: Float, data: Any? = null, viewRadius: Int = Int.MAX_VALUE) {
        repeat(amount) {
            val loc = player.location.clone()
            player.world.playEffect(
                loc
                    .add(0.0, 0.9, 0.0)
                    .add(RandomPointInSphere.generate(spawnRadius))
                , effect, data, viewRadius
            )
        }
    }
    fun playParticleInSphereWithPackets(location: Location, particleType: EnumParticle, amount: Int, spawnRadius: Float, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        repeat(amount) {
            val spawn = location
                .add(RandomPointInSphere.generate(spawnRadius))
            val particle = PacketPlayOutWorldParticles(particleType, longDistance, spawn.x.toFloat(), spawn.y.toFloat(), spawn.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in location.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }

    fun playParticleInSphereWithPackets(player: Player, particleType: EnumParticle, amount: Int, spawnRadius: Float, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        repeat(amount) {
            val spawn = player.location
                .add(0.0, 0.9, 0.0)
                .add(RandomPointInSphere.generate(spawnRadius))
            val particle = PacketPlayOutWorldParticles(particleType, longDistance, spawn.x.toFloat(), spawn.y.toFloat(), spawn.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in player.location.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }
}