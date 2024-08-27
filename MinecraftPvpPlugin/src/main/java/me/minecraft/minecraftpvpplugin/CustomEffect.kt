package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.EffectShape.*
import me.minecraft.minecraftpvpplugin.helpers.RandomPointInSphere
import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class EffectShape {
    InBall,
    InGaussian,
    InGaussianDisk,
    OnCircle
}

object CustomEffect {
    private val random = Random()

    fun playParticle(location: Location, effect: Effect, amount: Int, shape: EffectShape, radius: Float, data: Any? = null, viewRadius: Int = 10000) {
        var angleRadians = 0.0

        repeat(amount) {
            val loc = randomizeLocation(location, shape, radius)

            if (shape == OnCircle) {
                angleRadians += 2 * PI / amount
                loc.add(cos(angleRadians) * radius, 0.0, sin(angleRadians) * radius)
            }

            loc.world.playEffect(loc, effect, data, viewRadius)
        }
    }

    fun playParticle(player: Player, effect: Effect, amount: Int, shape: EffectShape, radius: Float, data: Any? = null, viewRadius: Int = 10000) {
        var angleRadians = 0.0

        repeat(amount) {
            val loc = randomizeLocation(player.location, shape, radius).add(0.0, 0.9, 0.0)

            if (shape == OnCircle) {
                angleRadians += 2 * PI / amount
                loc.add(cos(angleRadians) * radius, 0.0, sin(angleRadians) * radius)
            }

            player.world.playEffect(loc, effect, data, viewRadius)
        }
    }

    fun playParticleWithPackets(location: Location, particleType: EnumParticle, amount: Int, shape: EffectShape, radius: Float, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        var angleRadians = 0.0

        repeat(amount) {
            val loc = randomizeLocation(location, shape, radius)

            if (shape == OnCircle) {
                angleRadians += 2 * PI / amount
                loc.add(cos(angleRadians) * radius, 0.0, sin(angleRadians) * radius)
            }

            val particle = PacketPlayOutWorldParticles(particleType, longDistance, loc.x.toFloat(), loc.y.toFloat(), loc.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in loc.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }

    fun playParticleWithPackets(player: Player, particleType: EnumParticle, amount: Int, shape: EffectShape, radius: Float, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        var angleRadians = 0.0

        repeat(amount) {
            val loc = randomizeLocation(player.location, shape, radius).add(0.0, 0.9, 0.0)

            if (shape == OnCircle) {
                angleRadians += 2 * PI / amount
                loc.add(cos(angleRadians) * radius, 0.0, sin(angleRadians) * radius)
            }

            val particle = PacketPlayOutWorldParticles(particleType, longDistance, loc.x.toFloat(), loc.y.toFloat(), loc.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in player.location.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }

    private fun randomizeLocation(location: Location, shape: EffectShape, radius: Float): Location {
        val loc = location.clone()
        when (shape) {
            InBall -> loc.add(RandomPointInSphere.generate(radius))
            InGaussian -> loc.add(
                random.nextGaussian() * radius,
                random.nextGaussian() * radius,
                random.nextGaussian() * radius
            )
            InGaussianDisk -> loc.add(
                random.nextGaussian() * radius,
                0.0,
                random.nextGaussian() * radius
            )
            else -> {}
        }
        return loc
    }
}