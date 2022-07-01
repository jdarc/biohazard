package com.zynaps.biohazard

class Island(val tribes: List<Tribe>) : Population {

    val competitions = mutableListOf<Competition>()

    override var threaded
        get() = tribes.first().threaded
        set(value) = tribes.forEach { it.threaded = value }

    override val champion get() = (tribes.maxByOrNull { it.champion.fitness } ?: tribes.first()).champion

    override val generation get() = tribes.first().generation

    override var crossoverRate
        get() = tribes.first().crossoverRate
        set(value) = tribes.forEach { it.crossoverRate = value }

    override var mutationRate
        get() = tribes.first().mutationRate
        set(value) = tribes.forEach { it.mutationRate = value }

    override fun apply(callback: (Creature) -> Unit) = tribes.forEach { it.apply(callback) }

    override fun evolve(callback: (Creature) -> Double) {
        (if (threaded) tribes.parallelStream() else tribes.stream()).forEach { it.evolve(callback) }
        if (competitions.isNotEmpty() && tribes.size > 1) competitions.forEach { it.invoke(tribes) }
    }

    override fun describe(): String {
        val sep = System.lineSeparator()
        val tribal = tribes.joinToString(sep) { "  ${it.describe()}" }
        return "Island(competitions=${competitions.size}) {$sep$tribal$sep}"
    }
}
