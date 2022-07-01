package com.zynaps.biohazard.selectors

import com.zynaps.biohazard.Creature
import com.zynaps.biohazard.generators.Rng

fun interface SelectionScheme {
    fun apply(src: List<Creature>, rng: Rng): List<Creature>
}
