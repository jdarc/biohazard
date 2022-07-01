package demo

internal object Calculator {
    private val KEYS = ('a'..'z').toList().toCharArray()
    private val OPERATORS = charArrayOf('/', '*', '-', '+')
    private val FUNCTIONS = arrayOf<(Double, Double) -> Double>(
        { a, b -> a / b },
        { a, b -> a * b },
        { a, b -> a - b },
        { a, b -> a + b }
    )

    fun eval(s: String): Double {
        var equation = s

        var kdx = 0
        val map = mutableListOf<MathOp>()
        for (i in OPERATORS.indices) {
            val operator = OPERATORS[i]
            var index = equation.indexOf(operator)
            while (index != -1) {
                val l = getLhs(equation, index)
                val r = getRhs(equation, index)
                map.add(MathOp(i, equation.substring(l, index), equation.substring(index + 1, r)))
                equation = "${equation.substring(0, l)}${KEYS[kdx++]}${equation.substring(r)}"
                index = equation.indexOf(operator)
            }
        }

        return resolve(equation[0], map)
    }

    private fun getRhs(equation: String, start: Int): Int {
        val i = equation.indexOfAny(OPERATORS, start + 1)
        return if (i == -1) equation.length else i
    }

    private fun getLhs(equation: String, start: Int): Int {
        val i = equation.lastIndexOfAny(OPERATORS, start - 1) + 1
        return if (i == -1) 0 else i
    }

    private fun isKey(s: String) = s.length == 1 && s[0] in 'a'..'z'

    private fun resolve(key: Char, ops: List<MathOp>): Double {
        val op = ops[key - 'a']
        val x = if (isKey(op.lhs)) resolve(op.lhs[0], ops) else op.lhs.toDouble()
        val y = if (isKey(op.rhs)) resolve(op.rhs[0], ops) else op.rhs.toDouble()
        return FUNCTIONS[op.fn](x, y)
    }

    private class MathOp(val fn: Int, val lhs: String, val rhs: String)
}
