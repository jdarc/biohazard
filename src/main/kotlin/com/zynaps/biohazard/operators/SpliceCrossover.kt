package com.zynaps.biohazard.operators

import com.zynaps.biohazard.generators.Rng

class SpliceCrossover : CrossoverOperator {
    override fun accept(src1: BooleanArray, src2: BooleanArray, dst: BooleanArray, rate: Double, rng: Rng) {
        src1.copyInto(dst)
        if (rng.next() < rate) {
            val srcPos = rng.nextInt(src2.size - 1)
            val srcLen = rng.nextInt(src2.size - srcPos + 1)
            val dstPos = rng.nextInt(dst.size - srcLen + 1)
            System.arraycopy(src2, srcPos, dst, dstPos, srcLen)
        }
    }
}
