package com.zynaps.biohazard.selectors

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.generators.Rng

class RouletteSelection(elitism: Double) : SelectionScheme {

    private val elitism = 1.0 - elitism.coerceIn(0.0, 1.0)

    override fun apply(src: List<Creature>, rng: Rng): List<Creature> {
        val min = src.minOf { it.fitness } - 1.0
        val sum = src.sumOf { it.fitness - min }
        val critters = src.sortedByDescending { it.fitness }
        return List(src.size) { wheel(critters, min - Double.MIN_VALUE, sum * rng.next() * elitism) }
    }

    private fun wheel(creatures: Collection<Creature>, min: Double, slice: Double): Creature {
        var accum = 0.0
        for (creature in creatures) {
            accum += creature.fitness - min
            if (accum >= slice) return creature
        }
        return creatures.first()
    }

    override fun toString() = "RouletteSelection[elitism=$elitism]"
}
