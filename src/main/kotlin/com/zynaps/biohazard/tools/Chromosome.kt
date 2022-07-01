package com.zynaps.biohazard.tools

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.generators.Rng
import com.zynaps.biohazard.generators.ThreadLocalRng

object Chromosome {

    class RandomizeGenes(private val rng: Rng = ThreadLocalRng()) : (Creature) -> Unit {
        override fun invoke(creature: Creature) {
            for (i in creature.chromosome.indices) creature.chromosome[i] = rng.next() > 0.5
        }
    }

    class ZeroGenes : (Creature) -> Unit {
        override fun invoke(creature: Creature) = creature.chromosome.fill(false)
    }
}
