package me.minecraft.minecraftpvpplugin.helpers

import org.bukkit.util.Vector
import kotlin.random.Random

object RandomPointInSphere {
    fun generate(radius: Float): Vector {
        var keepGenerating = true
        var x = 0f
        var y = 0f
        var z = 0f

        while (keepGenerating) {
            x = Random.nextFloat() * 2 - 1
            y = Random.nextFloat() * 2 - 1
            z = Random.nextFloat() * 2 - 1

            keepGenerating = x*x + y*y + z*z > 1
        }

        return Vector(x, y, z).multiply(radius)
    }
}