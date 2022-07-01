package demo.worms

import com.zynaps.biohazard.Builder
import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.competitions.BattleArena
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.selectors.TournamentSelection
import demo.Automata
import demo.Helpers.pack
import demo.worms.world.Grid
import demo.worms.world.Processor
import demo.worms.world.Worm
import kotlin.math.max

internal class Controller(private val notify: (Int, Double, Double, Grid) -> Unit) {
    private val processors = ThreadLocal.withInitial { Processor() }
    private val memory = ThreadLocal.withInitial { IntArray(Processor.PROGRAM_SIZE) }
    private val grids = ThreadLocal.withInitial { Grid(GRID_SIZE) }

    private var interactive = false
    private var lastFitness = 0.0
    private var maxFitness = Double.NEGATIVE_INFINITY
    private var lastTime = 0L
    private val automata = Automata(GRID_SIZE - 2, GRID_SIZE - 2)

    private val population = Builder()
        .threaded(true)
        .tribes(4)
        .capacity(50)
        .genes(Processor.PROGRAM_SIZE * Processor.INSTRUCTION_SIZE)
        .selectionScheme(TournamentSelection(5))
        .crossoverRate(0.8)
        .mutationRate(0.0005, 0.001, 0.0015, 0.002)
        .competitions(BattleArena(0.02, 0.1, ThreadLocalRng()))
        .build()

    var replayChampion = true

    fun start() {
        println(population.describe())

        Thread {
            while (true) {
                if (System.currentTimeMillis() - lastTime > GRID_CHANGE_MILLIS) {
                    lastTime = System.currentTimeMillis()
                    automata.seed = System.nanoTime().toString()
                    automata.randomFillPercent = 47
                    automata.generate()
                }

                population.evolve { creature -> evaluate(creature) }

                if (replayChampion && lastFitness != population.champion.fitness) {
                    lastFitness = population.champion.fitness
                    maxFitness = max(lastFitness, maxFitness)
                    interactive = true
                    val t = System.currentTimeMillis()
                    evaluate(population.champion)
                    lastTime -= System.currentTimeMillis() - t
                    interactive = false
                }

                Thread.sleep(0)
            }
        }.start()
    }

    private fun evaluate(creature: Creature): Double {
        val processor = processors.get()
        val grid = grids.get()
        grid.configure(automata)

        val worm = Worm()
        worm.reset()
        worm.moveTo(128, 128)

        processor.load(*compile(creature))

        var step = 0
        var fitness = 0.0
        while (worm.isAlive) {
            grid.set(worm.x, worm.y)
            worm.sense(grid)

            processor.clear()
            processor.poke(0, ++step)
            processor.poke(1, worm.x)
            processor.poke(2, worm.y)
            processor.poke(3, worm.energy)
            processor.poke(4, worm.orientation.ordinal)
            processor.poke(5, worm.wallAntennae.sw)
            processor.poke(6, worm.wallAntennae.w)
            processor.poke(7, worm.wallAntennae.nw)
            processor.poke(8, worm.wallAntennae.n)
            processor.poke(9, worm.wallAntennae.ne)
            processor.poke(10, worm.wallAntennae.e)
            processor.poke(11, worm.wallAntennae.se)
            processor.poke(12, worm.foodAntennae.sw)
            processor.poke(13, worm.foodAntennae.w)
            processor.poke(14, worm.foodAntennae.nw)
            processor.poke(15, worm.foodAntennae.n)
            processor.poke(16, worm.foodAntennae.ne)
            processor.poke(17, worm.foodAntennae.e)
            processor.poke(18, worm.foodAntennae.se)
            processor.run(10000)

            val turnPeek = processor.peek(0)
            when {
                turnPeek < -512.0 -> worm.turnLeft()
                turnPeek > 512.0 -> worm.turnRight()
                else -> worm.moveForward()
            }

            when {
                grid.isObstacle(worm.x, worm.y) || worm.energy < 1 -> worm.kill()
                grid.isFood(worm.x, worm.y) -> {
                    worm.addEnergy(5)
                    fitness += 1.0
                }
                else -> worm.useEnergy(1)
            }

            fitness += 0.5

            if (interactive) notify(population.generation, population.champion.fitness, maxFitness, grid)
        }
        return fitness
    }

    private fun compile(creature: Creature): IntArray {
        val assembly = memory.get()
        for (i in assembly.indices) {
            assembly[i] = pack(creature.chromosome, Processor.INSTRUCTION_SIZE, Processor.INSTRUCTION_SIZE * i).toInt()
        }
        return assembly
    }

    companion object {
        private const val GRID_CHANGE_MILLIS = 1000 * 60 * 15
        const val GRID_SIZE = 256
    }
}
