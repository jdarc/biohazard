package com.zynaps.biohazard.selectors

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Test

internal class RouletteSelectionTest {

    @Test
    fun testFromZeroFitness() {
        val creatures = doubleArrayOf(20.0, 0.0, 30.0, 10.0).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.0).apply(creatures, CannedRandomNumber(0.0, 0.49, 0.83, 0.99))
        check(selected, creatures)
    }

    @Test
    fun testAboveZeroFitness() {
        val creatures = doubleArrayOf(15.0, 5.0, 20.0, 10.0).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.0).apply(creatures, CannedRandomNumber(0.0, 0.49, 0.83, 0.99))
        check(selected, creatures)
    }

    @Test
    fun testFullRangeFitness() {
        val creatures = doubleArrayOf(50.0, -10.0, 100.0, 0.0).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.0).apply(creatures, CannedRandomNumber(0.0, 0.61, 0.95, 0.995))
        check(selected, creatures)
    }

    @Test
    fun testUpToZeroFitness() {
        val creatures = doubleArrayOf(-50.0, -10.0, -30.0, 0.0).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.0).apply(creatures, CannedRandomNumber(0.0, 0.48, 0.81, 0.995))
        check(selected, creatures)
    }

    @Test
    fun testBelowZeroFitness() {
        val creatures = doubleArrayOf(-50.0, -10.0, -30.0, -5.0).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.0).apply(creatures, CannedRandomNumber(0.0, 0.48, 0.81, 0.995))
        check(selected, creatures)
    }

    @Test
    fun testElitism() {
        val creatures = doubleArrayOf(5.0, 5.1, 5.2, 5.3).map { Creature(0, fitness = it) }
        val selected = RouletteSelection(0.25).apply(creatures, CannedRandomNumber(0.0, 0.25, 0.5, 0.75))
        assertThat(selected[0].fitness, Is(creatures[3].fitness))
        assertThat(selected[1].fitness, Is(creatures[3].fitness))
        assertThat(selected[2].fitness, Is(creatures[2].fitness))
        assertThat(selected[3].fitness, Is(creatures[1].fitness))
    }

    private fun check(selected: List<Creature>, original: List<Creature>) {
        assertThat(selected.map { it.fitness }, Is(equalTo(original.map { it.fitness }.sortedDescending())))
    }
}
