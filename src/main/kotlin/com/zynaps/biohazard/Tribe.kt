package com.zynaps.biohazard

import com.zynaps.biohazard.generators.Rng
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.operators.BitFlipMutator
import com.zynaps.biohazard.operators.CrossoverOperator
import com.zynaps.biohazard.operators.MutationOperator
import com.zynaps.biohazard.operators.SpliceCrossover
import com.zynaps.biohazard.selectors.SelectionScheme
import com.zynaps.biohazard.selectors.TournamentSelection
import com.zynaps.biohazard.tools.Shuffler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class Tribe(capacity: Int, generator: () -> Creature) : Population {
    override var threaded = true

    override val champion = generator()

    var random: Rng = ThreadLocalRng()
    var selectionScheme: SelectionScheme = TournamentSelection(3)
    var crossoverOperator: CrossoverOperator = SpliceCrossover()
    var mutationOperator: MutationOperator = BitFlipMutator()

    internal var parents = List(capacity) { generator() }
    private var children = List(capacity) { generator() }

    override var generation = 0
        private set

    override var crossoverRate = 0.8
        set(value) {
            field = value.coerceIn(0.0, 1.0)
        }

    override var mutationRate = 0.005
        set(value) {
            field = value.coerceIn(0.0, 1.0)
        }

    override fun apply(callback: (Creature) -> Unit) = parents.forEach { callback(it) }

    override fun evolve(callback: (Creature) -> Double) {
        compete(callback)
        breed()
        mutate()
        swap()
        ++generation
    }

    override fun describe(): String {
        val cop = "${crossoverOperator.javaClass.simpleName}[r=$crossoverRate]"
        val mop = "${mutationOperator.javaClass.simpleName}[r=$mutationRate]"
        return "Tribe(threaded=$threaded, capacity=${parents.size}, genes=${parents[0].genes}, $selectionScheme, $cop, $mop)"
    }

    internal fun compete(callback: (Creature) -> Double) {
        execute(parents) { it.fitness = callback(it) }
        champion.copy(parents.sortedByDescending { it.fitness }.getOrElse(0) { champion })
    }

    internal fun breed() {
        val i = AtomicInteger(0)
        val candidates = Shuffler.shuffle(selectionScheme.apply(parents, random), random)
        execute(children) {
            val index = i.getAndIncrement() * 2
            val mum = candidates[(index + 0) % candidates.size]
            val dad = candidates[(index + 1) % candidates.size]
            crossoverOperator.accept(mum.chromosome, dad.chromosome, it.chromosome, crossoverRate, random)
        }
    }

    internal fun mutate() =
        execute(children) { mutationOperator.accept(it.chromosome, it.chromosome, mutationRate, random) }

    internal fun swap() {
        parents = children.also { children = parents }
    }

    private inline fun <T> execute(items: List<T>, crossinline cb: (T) -> Unit) = if (threaded) {
        par.submit { items.parallelStream().forEach { cb(it) } }.get()
    } else {
        items.forEach { cb(it) }
    }

    private companion object {
        val CPUS = Runtime.getRuntime().availableProcessors()

        val par: ExecutorService = Executors.newWorkStealingPool(CPUS + CPUS / 2)
    }
}
