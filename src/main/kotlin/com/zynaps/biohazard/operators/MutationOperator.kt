package com.zynaps.biohazard.operators

import com.zynaps.biohazard.generators.Rng

fun interface MutationOperator {
    fun accept(src: BooleanArray, dst: BooleanArray, rate: Double, rng: Rng)
}
