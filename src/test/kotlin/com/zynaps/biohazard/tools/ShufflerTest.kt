package com.zynaps.biohazard.tools

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.helpers.CannedRandomNumber
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.`is` as Is

internal class ShufflerTest {

    private val rng = ThreadLocalRng()

    @Test
    fun `should return an empty list if the input list is empty`() {
        val result = Shuffler.shuffle(emptyList<Creature>(), rng)
        assertThat(result.isEmpty(), Is(true))
    }

    @Test
    fun `should return a copy if the input list contains a single item`() {
        val result = Shuffler.shuffle(listOf("1"), rng)
        assertThat(result[0], Is("1"))
    }

    @Test
    fun `should return the same number of items as input list`() {
        val list = listOf(1, 2, 1, 4, 0, 6, 7, 8)
        val result = Shuffler.shuffle(list, rng)
        assertThat(result.size, Is(list.size))
    }

    @Test
    fun `should ensure the same item is not returned twice`() {
        val list = listOf(1, 21, 3, 14, 5, -6, 7)
        val result = Shuffler.shuffle(list, rng)
        assertThat(result.toSet().size, Is(list.size))
    }

    @Test
    fun `should return a shuffled list`() {
        val list = listOf(1, 21, 3, 14, 5, -6, 7)
        val result = Shuffler.shuffle(list, CannedRandomNumber(0.1, 0.7, 0.4, 0.3))
        assertThat(result, Is(not(equalTo(list))))
    }
}
