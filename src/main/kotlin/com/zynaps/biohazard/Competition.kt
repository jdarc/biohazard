package com.zynaps.biohazard

fun interface Competition {
    fun invoke(tribes: List<Tribe>)
}