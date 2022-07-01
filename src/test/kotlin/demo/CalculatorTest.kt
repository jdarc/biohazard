package demo

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs

internal class CalculatorTest {

    private val large = intArrayOf(25, 50, 75, 100)
    private val small = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    private val operands = charArrayOf('+', '-', '*', '/')
    private val rng = ThreadLocalRandom.current()

    @Test
    fun match() {
        val numbers = IntArray(6)
        for (i in 0 until 100) {
            val eq = generateEquation(chooseNumbers(numbers))
            val actual = Calculator.eval(eq)
            val expected = Helpers.evalStringEq(eq)
            assertThat(abs(actual - expected), Matchers.lessThan(0.000001))
        }
    }

    @Test
    @Disabled
    fun benchmark() {
        val numbers = IntArray(6)
        val eq = generateEquation(chooseNumbers(numbers))

        var cap = Calculator.eval(eq)
        val t0 = System.currentTimeMillis()
        for (i in 0 until 1000000) {
            cap += Calculator.eval(eq)
            cap -= Calculator.eval(eq)
        }
        println("$cap after ${(System.currentTimeMillis() - t0) / 1000.0} seconds")

        cap = Helpers.evalStringEq(eq)
        val t1 = System.currentTimeMillis()
        for (i in 0 until 1000000) {
            cap += Helpers.evalStringEq(eq)
            cap -= Helpers.evalStringEq(eq)
        }
        println("$cap after ${(System.currentTimeMillis() - t1) / 1000.0} seconds")
    }

    private fun generateEquation(numbers: IntArray): String {
        val sequence = numbers.mapIndexed { _, it -> "${operands[rng.nextInt(operands.size)]}${it}" }
        return sequence.joinToString("").substring(1)
    }

    private fun chooseNumbers(numbers: IntArray): IntArray {
        large.shuffle()
        large.copyInto(numbers, 0, 0, 2)
        small.shuffle()
        small.copyInto(numbers, 2, 0, 4)
        return numbers
    }
}
