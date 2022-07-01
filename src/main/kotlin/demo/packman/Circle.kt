package demo.packman

import demo.Helpers.sqr
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.sqrt

internal class Circle(val x: Double, val y: Double, val radius: Double) {

    fun overlap(other: Circle): Double {
        var d = sqr(x - other.x) + sqr(y - other.y)
        if (d > (radius + other.radius) * (radius + other.radius)) return 0.0
        d = sqrt(d)
        val overlaps = d <= abs(other.radius - radius)
        return when {
            overlaps && other.radius >= radius -> kotlin.math.PI * radius * radius
            overlaps -> kotlin.math.PI * other.radius * other.radius
            else -> {
                val rr0 = other.radius * other.radius
                val rr1 = radius * radius
                val phi = 2.0 * acos((rr0 + d * d - rr1) / (2.0 * other.radius * d))
                val theta = 2.0 * acos((rr1 + d * d - rr0) / (2.0 * radius * d))
                0.5 * theta * rr1 - 0.5 * rr1 * sin(theta) + 0.5 * phi * rr0 - 0.5 * rr0 * sin(phi)
            }
        }
    }
}
