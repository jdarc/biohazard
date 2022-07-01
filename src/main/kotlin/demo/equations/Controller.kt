package demo.equations

import com.zynaps.biohazard.Builder
import com.zynaps.biohazard.Population
import com.zynaps.biohazard.generators.ThreadLocalRng
import com.zynaps.biohazard.selectors.RouletteSelection
import demo.Calculator
import demo.Helpers
import java.util.*
import kotlin.math.abs

internal class Controller private constructor(totalLarge: Int) {
    private val numbers = pickNumbers(totalLarge.coerceIn(1, 4)).toIntArray()
    private val target = ThreadLocalRng().nextInt(998) + 1.0

    private fun run(population: Population) {
        var lastResult = 0.0

        do {
            population.evolve {
                val decoded = Formula(it)
                if (decoded.isValid) target - abs(target - evaluate(decoded.toEquation(numbers))) else 0.0
            }

            val formula = Formula(population.champion).toEquation(numbers)
            val result = evaluate(formula)
            if (lastResult != result) {
                lastResult = result
                println("Generation [${population.generation}]: $formula = $lastResult")
            }
        } while (lastResult != target || population.champion.fitness <= 0.0)

        val formula = Formula(population.champion).toEquation(numbers)
        val actual = evaluate(formula)
        val expected = Helpers.evalStringEq(formula)
        val isValid = abs(actual - expected) < 0.00001
        val message = if (isValid) {
            "${describe()}${System.lineSeparator()}Solution: $formula = ${actual.toInt()}"
        } else {
            "ERROR!!! Expected: $expected Actual: $actual"
        }
        println()
        println(message)
    }

    private fun describe() = "Numbers: ${numbers.contentToString()} -> Target: ${target.toInt()}"

    private fun evaluate(equation: String) = Calculator.eval(equation)

    private fun pickNumbers(large: Int) = LARGE.shuffled().take(large) + SMALL.shuffled().take(MAX_NUMBERS - large)

    companion object {
        const val MAX_NUMBERS = 6
        val LARGE = listOf(25, 50, 75, 100)
        val SMALL = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        fun play() {
            val population = Builder().capacity(200).genes(32).selectionScheme(RouletteSelection(0.5)).build()

            val message = """
                Genetic Algorithm: Equations
                
                ${population.describe()}
                
                How many large numbers? 1, 2, 3, or 4...
            """.trimIndent()

            println(message)
            val input = Scanner(System.`in`)
            val game = Controller(input.nextInt())
            println(game.describe())
            input.nextLine()
            println("Press enter key to solve...")
            input.nextLine()

            game.run(population)
        }
    }
}
