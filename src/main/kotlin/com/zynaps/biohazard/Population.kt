package com.zynaps.biohazard

interface Population {
    var threaded: Boolean

    val generation: Int
    val champion: Creature

    var crossoverRate: Double
    var mutationRate: Double

    fun apply(callback: (Creature) -> Unit)
    fun evolve(callback: (Creature) -> Double)

    fun describe(): String
}
