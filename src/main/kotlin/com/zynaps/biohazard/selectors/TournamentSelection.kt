package com.zynaps.biohazard.selectors

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.generators.Rng

class TournamentSelection(arity: Int) : SelectionScheme {

    private val arity = arity.coerceAtLeast(1)

    override fun apply(src: List<Creature>, rng: Rng) = src.indices.map {
        var selected = src[rng.nextInt(src.size)]
        for (j in 1 until arity) {
            val candidate = src[rng.nextInt(src.size)]
            if (candidate.fitness > selected.fitness) {
                selected = candidate
            }
        }
        selected
    }

    override fun toString() = "TournamentSelection[arity=$arity]"
}
