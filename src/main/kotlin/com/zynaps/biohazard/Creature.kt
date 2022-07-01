package com.zynaps.biohazard

import java.lang.System.arraycopy

class Creature(val genes: Int = 32, var fitness: Double = 0.0, generator: (Int) -> Boolean = { false }) :
    Comparable<Creature> {

    val chromosome = BooleanArray(genes) { generator(it) }

    operator fun get(index: Int) = chromosome[index]

    fun flip(index: Int) {
        chromosome[index] = !chromosome[index]
    }

    fun copy(other: Creature) {
        arraycopy(other.chromosome, 0, chromosome, 0, other.chromosome.size)
        fitness = other.fitness
    }

    fun extract(offset: Int, length: Int) = BooleanArray(length) { chromosome[offset + it] }

    fun splice(other: Creature, srcPos: Int, dstPos: Int, length: Int) =
        arraycopy(other.chromosome, srcPos, chromosome, dstPos, length)

    fun splice(sequence: String, offset: Int = 0) = (sequence.indices).forEach {
        chromosome[offset + it] = sequence[it] == '1'
    }

    fun describe(offset: Int = 0, length: Int = chromosome.size) = String(CharArray(length) {
        if (chromosome[offset + it]) '1' else '0'
    })

    override fun compareTo(other: Creature) = other.fitness.compareTo(fitness)

    override fun toString() = "Creature{dna=${chromosome.contentToString()}, fitness=$fitness}"

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Creature
        return fitness == other.fitness && chromosome.contentEquals(other.chromosome)
    }
}
