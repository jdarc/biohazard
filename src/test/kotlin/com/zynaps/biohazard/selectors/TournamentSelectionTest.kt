package com.zynaps.biohazard.selectors

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.`is` as Is
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.concurrent.ThreadLocalRandom

internal class TournamentSelectionTest {

    private val rng = ThreadLocalRandom.current()

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5])
    fun testArity(arity: Int) {
        val creatures = (0 until rng.nextInt(1, 9)).map { Creature(0, rng.nextDouble(-1000.0, 1000.0)) }
        val canned = (0 until creatures.size * arity).map { rng.nextDouble() }.toDoubleArray()
        val largest = findLargest(arity, creatures, canned)

        val result = TournamentSelection(arity).apply(creatures, CannedRandomNumber(*canned)).map { it.fitness }

        assertThat(result.size, Is(creatures.size))
        assertThat(result, Is(equalTo(largest)))
    }

    private fun findLargest(arity: Int, creatures: List<Creature>, canned: DoubleArray) = (0 until canned.size / arity)
        .map { canned.sliceArray(it * arity until it * arity + arity) }
        .map { it.maxOf { idx -> creatures[(idx * creatures.size).toInt()].fitness } }
}
