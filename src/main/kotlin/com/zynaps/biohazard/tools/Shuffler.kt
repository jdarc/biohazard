package com.zynaps.biohazard.tools

import com.zynaps.biohazard.generators.Rng

object Shuffler {

    fun <T> shuffle(src: List<T>, rng: Rng) = when {
        src.isEmpty() -> emptyList()
        src.size == 1 -> listOf(src[0])
        else -> {
            val indices = IntArray(src.size) { it }
            for (i in indices.size downTo 2) {
                val b = rng.nextInt(i)
                indices[i - 1] = indices[b].also { indices[b] = indices[i - 1] }
            }
            List(src.size) { index -> src[indices[index]] }
        }
    }
}
