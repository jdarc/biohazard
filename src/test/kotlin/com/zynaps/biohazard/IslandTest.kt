package com.zynaps.biohazard

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isIn
import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import org.hamcrest.Matchers.`is` as Is

internal class IslandTest {

    private val rnd = ThreadLocalRandom.current()

    @Test
    fun testGetChampion() {
        val island = Island(buildTribes(8))
        island.threaded = false

        var champion = island.tribes[0].champion
        var best = Double.NEGATIVE_INFINITY
        for (i in island.tribes.indices) {
            val creatures = island.tribes[i].parents
            for (creature in creatures) {
                creature.fitness = rnd.nextDouble(-1000.0, 1000.0)
                if (creature.fitness > best) {
                    best = creature.fitness
                    champion = creature
                }
            }
        }
        island.evolve { it.fitness }

        assertThat(island.champion.fitness, Is(champion.fitness))
    }

    @Test
    fun testGetGeneration() {
        val island = Island(buildTribes())
        island.threaded = false

        assertThat(island.generation, Is(0))

        island.evolve { 0.0 }
        assertThat(island.generation, Is(1))

        island.evolve { 0.0 }
        assertThat(island.generation, Is(2))
    }

    @Test
    fun testMutationRate() {
        val island = Island(buildTribes())
        island.threaded = false

        island.mutationRate = 0.008
        assertThat(island.mutationRate, Is(0.008))

        island.mutationRate = 0.003
        assertThat(island.mutationRate, Is(0.003))
    }

    @Test
    fun testMutationRateBounds() {
        val island = Island(buildTribes())
        island.threaded = false

        island.mutationRate = -0.1
        assertThat(island.mutationRate, Is(0.0))

        island.mutationRate = 1.1
        assertThat(island.mutationRate, Is(1.0))
    }

    @Test
    fun testCrossoverRate() {
        val island = Island(buildTribes())
        island.threaded = false

        island.crossoverRate = 0.3
        assertThat(island.crossoverRate, Is(0.3))

        island.crossoverRate = 0.5
        assertThat(island.crossoverRate, Is(0.5))
    }

    @Test
    fun testCrossoverRateBounds() {
        val island = Island(buildTribes())
        island.threaded = false

        island.crossoverRate = -0.1
        assertThat(island.crossoverRate, Is(0.0))

        island.crossoverRate = 1.1
        assertThat(island.crossoverRate, Is(1.0))
    }

    @Test
    fun testApply() {
        val island = Island(buildTribes(4))
        island.threaded = false

        island.tribes.forEach { it.parents.first().fitness = 1.0 }
        island.apply { it.fitness = 2.0 }

        island.tribes.forEach { assertThat(it.parents.first().fitness, Is(2.0)) }
    }

    @Test
    fun testEvolveSingleTribe() {
        val island = Island(buildTribes())
        island.threaded = false

        island.evolve { 10.0 }

        assertThat(island.tribes[0].champion.fitness, Is(10.0))
    }

    @Test
    fun testEvolveManyTribes() {
        val island = Island(buildTribes(2, 1))
        island.threaded = false

        val f = AtomicInteger(0)
        island.evolve { f.incrementAndGet().toDouble() }

        assertThat(island.tribes[0].champion.fitness, isIn(listOf(1.0, 2.0)))
        assertThat(island.tribes[1].champion.fitness, isIn(listOf(1.0, 2.0)))
    }

    @Test
    fun testFindChampion() {
        val island = Island(buildTribes(12))
        island.threaded = false

        island.tribes.forEach { it.champion.fitness = rnd.nextDouble() * 1000.0 }
        island.tribes[rnd.nextInt(island.tribes.size)].champion.fitness = 1984.0

        assertThat(island.champion.fitness, Is(1984.0))
    }

    private fun buildTribes(total: Int = 2, capacity: Int = 10) = List(total) { Tribe(capacity) { Creature(8) } }
}
