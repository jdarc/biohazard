package demo.packman

import kotlin.math.sqrt

internal class Edge(val p1: Vertex, val p2: Vertex) {

    val center = Vertex((p1.x + p2.x) * 0.5, (p1.y + p2.y) * 0.5)

    val normal = run {
        val x = p2.x - p1.x
        val y = p2.y - p1.y
        val len = sqrt(x * x + y * y)
        Vertex(-y / len, x / len)
    }
}
