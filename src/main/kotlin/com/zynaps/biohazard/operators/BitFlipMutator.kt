package com.zynaps.biohazard.operators

import com.zynaps.biohazard.generators.Rng

class BitFlipMutator : MutationOperator {
    override fun accept(src: BooleanArray, dst: BooleanArray, rate: Double, rng: Rng) {
        for (i in src.indices) {
            dst[i] = if (rng.next() < rate) !src[i] else src[i]
        }
    }
}
