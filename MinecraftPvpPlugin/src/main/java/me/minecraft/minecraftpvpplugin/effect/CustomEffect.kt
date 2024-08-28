package me.minecraft.minecraftpvpplugin.effect

import me.minecraft.minecraftpvpplugin.refs.JavaRandom
import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

open class Shape(amount: Int) {
    open fun generate(): List<Vector> {
        return listOf()
    }
}

class InBall(private val amount: Int, private val radius: Float) : Shape(amount) {
    override fun generate(): List<Vector> {
        return buildList {
            repeat(amount) {
                var keepGenerating = true
                var x = 0.0
                var y = 0.0
                var z = 0.0

                while (keepGenerating) {
                    x = Random.nextDouble() * 2 - 1
                    y = Random.nextDouble() * 2 - 1
                    z = Random.nextDouble() * 2 - 1

                    keepGenerating = x*x + y*y + z*z > 1
                }

                add(Vector(x, y, z).multiply(radius))
            }
        }
    }
}

class InGaussian(private val amount: Int, private val sizeBound: Vector) : Shape(amount) {
    override fun generate(): List<Vector> {
        return buildList {
            repeat(amount) {
                add(Vector(
                    JavaRandom.nextGaussian() * sizeBound.x,
                    JavaRandom.nextGaussian() * sizeBound.y,
                    JavaRandom.nextGaussian() * sizeBound.z
                ))
            }
        }
    }
}

class InGaussianBounded(private val amount: Int, private val boundSize: Vector) : Shape(amount) {
    override fun generate(): List<Vector> {
        return buildList {
            repeat(amount) {
                var keepGenerating = true
                var x = 0.0
                var y = 0.0
                var z = 0.0

                while (keepGenerating) {
                    x = JavaRandom.nextGaussian() * boundSize.x
                    y = JavaRandom.nextGaussian() * boundSize.y
                    z = JavaRandom.nextGaussian() * boundSize.z

                    keepGenerating = x < -boundSize.x / 2 || x > boundSize.x / 2 ||
                            y < -boundSize.y / 2 || y > boundSize.y / 2 ||
                            z < -boundSize.z / 2 || z > boundSize.z / 2
                }

                add(Vector(x, y, z))
            }
        }
    }
}

class OnCircle(private val amount: Int, private val radius: Float) : Shape(amount) {
    override fun generate(): List<Vector> {
        return buildList {
            var angleRadians = 0.0

            repeat(amount) {
                add(Vector(cos(angleRadians) * radius, 0.0, sin(angleRadians) * radius))
                angleRadians += 2 * PI / amount
            }
        }
    }
}

object CustomEffect {
    fun playParticle(location: Location, effect: Effect, shape: Shape, data: Any? = null, viewRadius: Int = 10000) {
        for (offset in shape.generate()) {
            location.world.playEffect(location.clone().add(offset), effect, data, viewRadius)
        }
    }

    fun playParticle(player: Player, effect: Effect, shape: Shape, data: Any? = null, viewRadius: Int = 10000) {
        for (offset in shape.generate()) {
            player.world.playEffect(player.location.clone().add(0.0, 0.9, 0.0).add(offset), effect, data, viewRadius)
        }
    }

    fun playParticleWithPackets(location: Location, particleType: EnumParticle, shape: Shape, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        for (offset in shape.generate()) {
            val loc = location.clone().add(offset)
            val particle = PacketPlayOutWorldParticles(particleType, longDistance, loc.x.toFloat(), loc.y.toFloat(), loc.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in location.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }

    fun playParticleWithPackets(player: Player, particleType: EnumParticle, shape: Shape, longDistance: Boolean = true, maxSpeed: Float = 0f) {
        for (offset in shape.generate()) {
            val loc = player.location.clone().add(0.0, 0.9, 0.0).add(offset)
            val particle = PacketPlayOutWorldParticles(particleType, longDistance, loc.x.toFloat(), loc.y.toFloat(), loc.z.toFloat(), 0f, 0f, 0f, maxSpeed, 0, 0, 0)

            for (playerInSameWorld in player.world.players) {
                (playerInSameWorld as CraftPlayer).handle.playerConnection.sendPacket(particle)
            }
        }
    }
}