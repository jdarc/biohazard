package demo.rostering

import com.zynaps.biohazard.Population

internal class Controller(
    schedule: Schedule,
    private val pop: Population,
    private val mapper: Mapper,
    private val rules: Rules
) {

    private val totalRequired = schedule.shifts.values.map { it.required }.reduce { a, b -> a + b }

    fun evolve(seconds: Int): List<Assignment> {
        println("Genetic Algorithm: Rostering")
        val timeLimit = System.currentTimeMillis() + seconds * 1000L

        var solutionFound = false
        var bestFitness = Double.NEGATIVE_INFINITY
        while (!solutionFound && System.currentTimeMillis() < timeLimit) {
            pop.evolve { rules.evaluate(mapper.decode(it)) }
            if (pop.champion.fitness != bestFitness) {
                bestFitness = pop.champion.fitness
                val decode = mapper.decode(pop.champion)
                solutionFound = decode.size == totalRequired
                println("Mutation: ${pop.mutationRate.toBigDecimal()}, Crossover: ${pop.crossoverRate.toBigDecimal()}, Generation: ${pop.generation}, Assignments: ${decode.size}/$totalRequired, Score: ${pop.champion.fitness}")
            }
        }

        return mapper.decode(pop.champion)
    }
}
