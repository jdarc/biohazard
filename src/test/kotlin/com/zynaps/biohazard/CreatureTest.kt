package com.zynaps.biohazard

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom
import org.hamcrest.Matchers.`is` as Is

internal class CreatureTest {

    private val random = ThreadLocalRandom.current()

    @Test
    fun testGenes() {
        val size = random.nextInt(0, 100)
        assertThat(Creature(size).describe(), Is("0".repeat(size)))
    }

    @Test
    fun testChromosomeSize() {
        val size = random.nextInt(100)
        assertThat(Creature(size).genes, Is(size))
    }

    @Test
    fun testFitness() {
        val fitness = random.nextDouble(-1000.0, 1000.0)
        assertThat(Creature(0, fitness).fitness, Is(fitness))
    }

    @Test
    fun testGenerator() {
        val genes = (0 until 10).map { random.nextBoolean() }.toBooleanArray()
        val chromosome = Creature(10, generator = { genes[it] }).chromosome

        assertThat(chromosome, Is(equalTo(genes)))
    }

    @Test
    fun testIndexer() {
        val creature = Creature(100, generator = { i -> i and 1 > 0 })
        val index = random.nextInt(creature.genes)

        assertThat(creature[index], Is(index and 1 != 0))
    }

    @Test
    fun testFlip() {
        val creature = Creature(100)
        val index = random.nextInt(creature.genes)

        creature.flip(index)

        assertThat(creature[index], Is(true))
    }

    @Test
    fun testMimic() {
        val fitness = random.nextDouble(-10.0, 10.0)
        val creature = Creature(8, fitness) { it == 2 }
        val theThing = Creature(8, 0.5) { it == 5 }

        theThing.copy(creature)

        assertThat(theThing.fitness, Is(fitness))
        assertThat(theThing.chromosome, Is(equalTo(creature.chromosome)))
    }

    @Test
    fun testExtract() {
        val code = "1001010101101111"
        val offset = random.nextInt(code.length / 2)
        val length = random.nextInt(code.length - offset) + 1

        val expected = code.substring((offset until offset + length)).map { it == '1' }.toBooleanArray()
        val actual = Creature(code.length) { code[it] == '1' }.extract(offset, length)

        assertThat(actual, Is(equalTo(expected)))
    }

    @Test
    fun testSplice() {
        val creature1 = Creature(15)
        val creature2 = Creature(15, generator = { "000001111000000"[it] == '1' })

        creature1.splice(creature2, 5, 10, 4)

        assertThat(creature1.describe(), Is("000000000011110"))
    }

    @Test
    fun testStringSplice() {
        val creature = Creature(11)

        creature.splice("1011101", 2)

        assertThat(creature.describe(), Is("00101110100"))
    }

    @Test
    fun testDescribe() {
        val dna = arrayOf(false, false, true, true, false, true, false, false, true, false, false, false)
        val creature = Creature(11, generator = { dna[it] })

        assertThat(creature.describe(), Is("00110100100"))
        assertThat(creature.describe(2, 7), Is("1101001"))
    }

    @Test
    fun testCompareTo() {
        val original = listOf(Creature(0, 1.0), Creature(0, 2.0), Creature(0, 3.0))
        assertThat(original.map { it.fitness }, Is(equalTo(listOf(1.0, 2.0, 3.0))))

        val sorted = original.sorted().map { it.fitness }
        assertThat(sorted, Is(equalTo(listOf(3.0, 2.0, 1.0))))
    }
}
