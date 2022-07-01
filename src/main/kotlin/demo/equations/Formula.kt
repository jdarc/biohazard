package demo.equations

import com.zynaps.biohazard.Creature
import demo.Helpers.pack

internal class Formula(creature: Creature) {
    private val numIndex: IntArray
    private val opsIndex: IntArray

    init {
        val genes = pack(creature.chromosome, 32, 0)
        numIndex = IntArray(6) { (0x7 and (genes shr 5 * it).toInt()) % 6 }
        opsIndex = IntArray(5) { (0x3 and (genes shr 5 * it + 3).toInt()) % 6 }
    }

    val isValid get() = numIndex.toSet().size == numIndex.size

    fun toEquation(numbers: IntArray): String {
        val nums = (numIndex.map { numbers[it] } + opsIndex.map { OPERANDS[it] }).toTypedArray<Any>()
        return String.format("%1\$s%7\$s%2\$s%8\$s%3\$s%9\$s%4\$s%10\$s%5\$s%11\$s%6\$s", *nums)
    }

    private companion object {
        val OPERANDS = charArrayOf('+', '-', '*', '/')
    }
}
