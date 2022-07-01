package demo.circles

import com.zynaps.biohazard.Builder
import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.tools.Chromosome.RandomizeGenes
import demo.Helpers.pack
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow

internal class Controller {
    private var population = Builder().capacity(500).genes(90).build()
    private var circles = emptyList<Circle>()
    private val scaleFactor = 2.0.pow(30.0)
    private val rng = ThreadLocalRandom.current()
    private var width = 0
    private var height = 0

    val staticCircles get() = circles
    val championCircle get() = decode(population.champion, width / scaleFactor)

    val fitness get() = population.champion.fitness
    val generation get() = population.generation

    fun evolve() = population.evolve(::computeFitness)

    fun reset(width: Int, height: Int) {
        this.width = width
        this.height = height
        circles = generateCircles()
        population.apply(RandomizeGenes())
    }

    private fun isInsideViewport(circle: Circle) =
        circle.x - circle.radius >= 0.0 && circle.x + circle.radius < width &&
                circle.y - circle.radius >= 0.0 && circle.y + circle.radius < height

    private fun computeFitness(creature: Creature) = decode(creature, width / scaleFactor).run {
        when {
            !isInsideViewport(this) || circles.any(this::overlaps) -> 0.0
            else -> this.radius
        }
    }

    private fun generateCircles() = mutableListOf<Circle>().apply {
        for (i in 0 until NUMBER_STATIC_CIRCLES) {
            val radius = rng.nextDouble(MINIMUM_RADIUS.toDouble(), MAXIMUM_RADIUS.toDouble()).pow(2)
            for (j in 0 until 10) {
                val x = rng.nextDouble(radius, width - radius)
                val y = rng.nextDouble(radius, height - radius)
                val circle = Circle(x, y, radius)
                if (this.none(circle::overlaps)) {
                    this.add(circle)
                    break
                }
            }
        }
    }

    private fun decode(creature: Creature, s: Double): Circle {
        val x = pack(creature.chromosome, 30, 0)
        val y = pack(creature.chromosome, 30, 30)
        val r = pack(creature.chromosome, 30, 60)
        return Circle(x * s, y * s, r * s)
    }

    companion object {
        private const val MINIMUM_RADIUS = 2
        private const val MAXIMUM_RADIUS = 8
        private const val NUMBER_STATIC_CIRCLES = 30
    }
}
