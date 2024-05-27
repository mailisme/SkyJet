package me.minecraft.minecraftpvpplugin.helpers

import java.util.*
import kotlin.random.Random

// Borrowed from https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java

class WeightedRandomChooser<E> {
    private val map: NavigableMap<Double, E> = TreeMap()
    private var total = 0.0

    fun addChoice(result: E, weight: Double = 1.0): WeightedRandomChooser<E> {
        if (weight <= 0) return this
        total += weight
        map[total] = result
        return this
    }

    fun addChoices(results: List<E>, weightSum: Double = 1.0): WeightedRandomChooser<E> {
        if (weightSum <= 0) return this
        val individualWeight = weightSum / results.size

        for (result in results) {
            addChoice(result, individualWeight)
        }
        return this
    }

    fun choose(): E {
        val value = Random.nextDouble() * total
        return map.higherEntry(value).value
    }
}