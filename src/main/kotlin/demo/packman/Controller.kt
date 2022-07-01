package demo.packman

import com.zynaps.biohazard.Builder
import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.competitions.BattleArena
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.selectors.TournamentSelection
import com.zynaps.biohazard.tools.Chromosome.RandomizeGenes
import demo.ConvexHull
import kotlin.math.pow

internal class Controller {

    private var randomize = false

    var shape = Shape()
        private set

    private val population = Builder()
        .tribes(3)
        .capacity(500)
        .genes(NUMBER_OF_CIRCLES * BITS * COMPONENTS)
        .mutationRate(0.003, 0.0025, 0.002)
        .selectionScheme(TournamentSelection(10))
        .competitions(BattleArena(0.01, 0.4, ThreadLocalRng()))
        .build()

    init {
        reset()
        println(population.describe())
    }

    val champion get() = if (population.champion.fitness > 0) decode(population.champion) else emptyArray()

    val generation get() = population.generation

    val fitness get() = population.champion.fitness

    fun evolve() {
        if (randomize) {
            randomize = false
            population.apply(RandomizeGenes(ThreadLocalRng()))
        }
        population.evolve { creature ->
            decode(creature).run {
                this.sumOf { circle ->
                    if (shape.contains(circle)) {
                        2.0 * circle.radius - this.filter { it != circle }.sumOf { it.overlap(circle) }
                    } else {
                        -circle.radius / 2.0
                    }
                }
            }
        }
    }

    fun reset() {
        shape = buildSquare()
        shape = Shape(*(ConvexHull.generate(20).map { Vertex(it.x * 7, it.y * 7) }).toTypedArray())
        randomize = true
    }

    companion object {
        private const val NUMBER_OF_CIRCLES = 16
        private const val COMPONENTS = 3
        private const val BITS = 16
        private val SCALE = 2.0.pow(BITS)
        private val NEG = SCALE / 2.0

        private fun buildSquare() = Shape(Vertex(-7.0, -7.0), Vertex(7.0, -7.0), Vertex(7.0, 7.0), Vertex(-7.0, 7.0))

        private fun decode(creature: Creature): Array<Circle> {
            val multiplier = 20.0 / SCALE
            return (0 until NUMBER_OF_CIRCLES).map { i ->
                val offset = i * BITS * COMPONENTS
                val x = demo.Helpers.pack(creature.chromosome, BITS, offset + 0, 3) - NEG
                val y = demo.Helpers.pack(creature.chromosome, BITS, offset + 1, 3) - NEG
                val r = demo.Helpers.pack(creature.chromosome, BITS, offset + 2, 3) / 2.0
                Circle(x * multiplier, y * multiplier, r * multiplier)
            }.toTypedArray()
        }
    }
}
