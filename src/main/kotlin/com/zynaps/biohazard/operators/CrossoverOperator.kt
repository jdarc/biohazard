package com.zynaps.biohazard.operators

import com.zynaps.biohazard.generators.Rng

fun interface CrossoverOperator {
    fun accept(src1: BooleanArray, src2: BooleanArray, dst: BooleanArray, rate: Double, rng: Rng)
}
