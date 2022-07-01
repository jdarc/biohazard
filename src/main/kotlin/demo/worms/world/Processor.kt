package demo.worms.world

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

internal class Processor {
    private val ins = IntArray(PROGRAM_SIZE)
    private val reg = DoubleArray(32)

    fun load(vararg code: Int) {
        code.copyInto(ins, 0, 0, code.size)
        ins[PROGRAM_SIZE - 1] = 0x0
    }

    fun clear() = reg.fill(0.0)

    fun peek(offset: Int) = reg[offset]

    fun poke(offset: Int, value: Int) {
        reg[offset] = value.toDouble()
    }

    fun run(nanos: Long) {
        val exeCap = System.nanoTime() + nanos.coerceIn(0L, Long.MAX_VALUE)
        var pc = 0
        while (System.nanoTime() < exeCap) {
            val instruction = ins[pc++]
            val opc = 0x01F and (instruction shr 27)
            val dst = 0x01F and (instruction shr 22)
            val opa = 0x01F and (instruction shr 17)
            val opb = 0x01F and (instruction shr 12)
            val mem = 0xFFF and instruction
            when (opc) {
                0x00, 0x10 -> return
                0x01, 0x11 -> pc = if (reg[opa] != 0.0) mem else pc
                0x02, 0x12 -> pc = if (reg[opa] > reg[opb]) mem else pc
                0x03, 0x13 -> pc = if (reg[opa] <= reg[opb]) mem else pc
                0x04, 0x14 -> reg[dst] = 0.0
                0x05, 0x15 -> reg[dst] = ((0x3FFFFF and instruction) - 0x200000).toDouble()
                0x06, 0x16 -> reg[dst] = ((0x3FFFFF and instruction) - 0x200000) / 512.0
                0x07, 0x17 -> reg[dst] = 1024.0
                0x08, 0x18 -> reg[dst] = -1024.0
                0x09, 0x19 -> reg[dst] = reg[opa] + reg[opb]
                0x0A, 0x1A -> reg[dst] = reg[opa] - reg[opb]
                0x0B, 0x1B -> reg[dst] = reg[opa] * reg[opb]
                0x0C, 0x1C -> reg[dst] = reg[opa] / reg[opb]
                0x0D, 0x1D -> reg[dst] = abs(reg[dst])
                0x0E, 0x1E -> reg[dst] = min(reg[opa], reg[opb])
                0x0F, 0x1F -> reg[dst] = max(reg[opa], reg[opb])
            }
            if (reg[dst].isInfinite()) {
                reg[dst] = 0.0
                return
            }
        }
    }

    companion object {
        const val PROGRAM_SIZE = 4096
        const val INSTRUCTION_SIZE = 32
    }
}
