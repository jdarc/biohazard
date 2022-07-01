package com.zynaps.biohazard.generators

import kotlin.math.max

fun interface Rng {
    fun next(): Double

    fun nextInt(bound: Int) = (max(0, bound) * next()).toInt()
}
