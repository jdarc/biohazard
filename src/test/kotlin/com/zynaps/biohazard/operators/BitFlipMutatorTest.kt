package com.zynaps.biohazard.operators

import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.`is` as Is
import org.junit.jupiter.api.Test

internal class BitFlipMutatorTest {

    private val size = 8

    @Test
    fun testBelowMutationRate() {
        val actual = BooleanArray(size)
        val expected = BooleanArray(size) { true }

        BitFlipMutator().accept(BooleanArray(size), actual, 0.5, CannedRandomNumber(0.49))

        assertThat(actual, Is(equalTo(expected)))
    }

    @Test
    fun testAboveMutationRate() {
        val actual = BooleanArray(size)
        val expected = BooleanArray(size) { false }

        BitFlipMutator().accept(BooleanArray(size), actual, 0.5, CannedRandomNumber(0.51))

        assertThat(actual, Is(equalTo(expected)))
    }
}
