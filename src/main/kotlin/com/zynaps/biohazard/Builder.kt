package com.zynaps.biohazard

import com.zynaps.biohazard.generators.Rng
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.operators.BitFlipMutator
import com.zynaps.biohazard.operators.CrossoverOperator
import com.zynaps.biohazard.operators.MutationOperator
import com.zynaps.biohazard.operators.SpliceCrossover
import com.zynaps.biohazard.selectors.SelectionScheme
import com.zynaps.biohazard.selectors.TournamentSelection
import com.zynaps.biohazard.tools.Chromosome

class Builder {
    private var threaded = true
    private var numTribes = 1
    private var numCreatures = 50
    private var numGenes = 64

    private var random: Rng = ThreadLocalRng()
    private var selectionScheme: SelectionScheme = TournamentSelection(8)
    private var crossoverOperator: CrossoverOperator = SpliceCrossover()
    private var mutationOperator: MutationOperator = BitFlipMutator()

    private var crossoverRate = doubleArrayOf(0.8)
    private var mutationRate = doubleArrayOf(0.0025)

    private var competitions = emptyList<Competition>()

    private var nuke = false

    fun threaded(value: Boolean) = apply { threaded = value }

    /** Configure the total number of tribes on the island. */
    fun tribes(value: Int) = apply { numTribes = value.coerceAtLeast(1) }

    /** Configure the total number of creatures per tribe. */
    fun capacity(value: Int) = apply { numCreatures = value.coerceAtLeast(2) }

    /** Configure the total number of genes all the creatures have. */
    fun genes(value: Int) = apply { numGenes = value.coerceAtLeast(8) }

    fun randomNumberGenerator(value: Rng) = apply { random = value }

    fun selectionScheme(value: SelectionScheme) = apply { selectionScheme = value }

    fun crossoverOperator(value: CrossoverOperator) = apply { crossoverOperator = value }

    fun mutationOperator(value: MutationOperator) = apply { mutationOperator = value }

    fun crossoverRate(vararg value: Double) = apply { crossoverRate = value.toTypedArray().toDoubleArray() }

    fun mutationRate(vararg value: Double) = apply { mutationRate = value.toTypedArray().toDoubleArray() }

    fun competitions(vararg values: Competition) = apply { this.competitions = values.toList() }

    fun nuke(value: Boolean) = apply { nuke = value }

    fun build(): Population {
        val pop = if (numTribes == 1) {
            configure(buildTribe(), 0)
        } else {
            val island = buildIsland()
            configure(island, 0)
            island.tribes.forEachIndexed { index, tribe -> configure(tribe, index) }
            island
        }
        return pop
    }

    private fun buildTribe(): Tribe {
        val tribe = Tribe(numCreatures) { Creature(numGenes) }
        tribe.random = random
        tribe.selectionScheme = selectionScheme
        tribe.crossoverOperator = crossoverOperator
        tribe.mutationOperator = mutationOperator
        return tribe
    }

    private fun buildIsland() =
        Island((0 until numTribes).map { buildTribe() }).also { it.competitions.addAll(competitions) }

    private fun configure(pop: Population, index: Int): Population {
        pop.threaded = threaded
        pop.crossoverRate = this.crossoverRate[index % this.crossoverRate.size]
        pop.mutationRate = this.mutationRate[index % this.mutationRate.size]
        if (nuke) pop.apply(Chromosome.RandomizeGenes(random))
        return pop
    }
}
