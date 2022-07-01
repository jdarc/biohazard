package demo.circles

import demo.Helpers.sqr

internal class Circle(val x: Double, val y: Double, val radius: Double) {
    fun overlaps(other: Circle) = sqr(x - other.x) + sqr(y - other.y) < sqr(radius + other.radius)
}
