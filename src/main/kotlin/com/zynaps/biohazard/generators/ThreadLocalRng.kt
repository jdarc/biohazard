package com.zynaps.biohazard.generators

import java.util.concurrent.ThreadLocalRandom

class ThreadLocalRng : Rng {
    override fun next() = ThreadLocalRandom.current().nextDouble()
}
