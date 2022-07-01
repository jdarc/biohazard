package com.zynaps.biohazard.helpers

import com.zynaps.biohazard.generators.Rng

internal class CannedRandomNumber(private vararg val values: Double, private var index: Int = 0) : Rng {
    override fun next() = values[index++ % values.size]
}
