package com.zynaps.biohazard.tools

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

internal class ChromosomeTest {

    @Test
    fun testZero() {
        val creature = Creature(10, generator = { true })
        Chromosome.ZeroGenes().invoke(creature)
        assertThat(creature.describe(), `is`("0000000000"))
    }

    @Test
    fun `should randomize chromosome`() {
        val creature = Creature(10)
        Chromosome.RandomizeGenes(CannedRandomNumber(1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0)).invoke(creature)
        assertThat(creature.describe(), `is`("1110100010"))
    }
}
