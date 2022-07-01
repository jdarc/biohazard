package com.zynaps.biohazard

import com.zynaps.biohazard.helpers.CannedRandomNumber
import com.zynaps.biohazard.operators.BitFlipMutator
import com.zynaps.biohazard.operators.CrossoverOperator
import com.zynaps.biohazard.selectors.SelectionScheme
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.sameInstance
import org.hamcrest.Matchers.`is` as Is
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

internal class TribeTest {

    @Test
    fun testCompete() {
        val samples = doubleArrayOf(1.0, 2.0, 3.0, 6.0, -3.0, -1.0, 8.0, 0.4, 1.0, 23.0, 10.0, 11.0, 33.0, 1.0, 3.0, -4.0, 1.0)
        val tribe = Tribe(samples.size) { Creature(8) }
        assertThat(tribe.champion.fitness, Is(0.0))

        val n = AtomicInteger(0)
        tribe.compete { samples[n.getAndIncrement()] }

        for (i in samples.indices) {
            assertThat(tribe.parents[i].fitness, Is(not(0.0)))
        }
        assertThat(tribe.champion.fitness, Is(33.0))
    }

    @Test
    fun testBreed() {
        val rng = CannedRandomNumber(0.0, 0.13, 0.25, 0.38, 0.5, 0.63, 0.75, 0.88, 0.99)

        val tribe = Tribe(4) { Creature(8) }
        tribe.random = rng
        tribe.selectionScheme = SelectionScheme { src, _ ->
            listOf(src[0], src[1], src[2], src[3], src[1], src[3], src[0], src[2])
        }
        tribe.crossoverOperator = CrossoverOperator { mum, dad, kid, _, _ ->
            mum.copyInto(kid, 0, 0, 4)
            dad.copyInto(kid, 4, 4, 8)
            Unit
        }

        tribe.threaded = false
        tribe.crossoverRate = 0.5
        tribe.parents[0].splice("11000011")
        tribe.parents[1].splice("11110000")
        tribe.parents[2].splice("00001111")
        tribe.parents[3].splice("00111100")

        tribe.breed()
        tribe.swap()

        assertThat(tribe.parents[0].describe(), Is("11001100"))
        assertThat(tribe.parents[1].describe(), Is("11111111"))
        assertThat(tribe.parents[2].describe(), Is("00110000"))
        assertThat(tribe.parents[3].describe(), Is("00000011"))
    }

    @Test
    fun testMutate() {
        val random = CannedRandomNumber(0.25, 0.75)
        val tribe = Tribe(4) { Creature(8) }

        tribe.random = random
        tribe.mutationOperator = BitFlipMutator()
        tribe.threaded = false

        tribe.mutationRate = 0.5
        tribe.swap()
        tribe.parents.forEach { assertThat(it.describe(), Is("00000000")) }

        tribe.mutate()
        tribe.swap()
        tribe.parents.forEach { assertThat(it.describe(), Is("10101010")) }
    }

    @Test
    fun testSwap() {
        val tribe = Tribe(0) { Creature(0) }
        val src = tribe.parents

        tribe.swap()
        assertThat(tribe.parents, Is(not(sameInstance(src))))

        tribe.swap()
        assertThat(tribe.parents, Is(sameInstance(src)))
    }
}
