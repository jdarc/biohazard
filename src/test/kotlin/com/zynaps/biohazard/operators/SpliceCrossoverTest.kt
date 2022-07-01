package com.zynaps.biohazard.operators

import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.`is` as Is

internal class SpliceCrossoverTest {

    @Test
    fun testAboveCrossoverRate() {
        val mum = BooleanArray(9) { true }
        val kid = BooleanArray(9)

        SpliceCrossover().accept(mum, BooleanArray(9), kid, 0.5, CannedRandomNumber(0.51))

        assertThat(describe(kid), Is(equalTo(describe(mum))))
    }

    @Test
    fun testBelowCrossoverRate() {
        val mum = BooleanArray(9) { true }
        val kid = BooleanArray(9)

        SpliceCrossover().accept(mum, BooleanArray(9), kid, 0.5, CannedRandomNumber(0.49))

        assertThat(describe(kid), Is(not(describe(mum))))
    }

    @Test
    fun testCrossoverRate() {
        val mum = BooleanArray(9) { true }
        val kid = BooleanArray(9)

        SpliceCrossover().accept(mum, BooleanArray(9), kid, 0.5, CannedRandomNumber(0.12, 0.05, 0.39, 0.68))

        assertThat(describe(kid), Is("111100011"))
    }

    private fun describe(kid: BooleanArray) = kid.joinToString("") { if (it) "1" else "0" }
}
