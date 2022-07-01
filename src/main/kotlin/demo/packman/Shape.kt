package demo.packman

internal class Shape(vararg vertices: Vertex) {

    val edges = vertices.mapIndexed { idx, vertex ->
        Edge(vertex, vertices[(idx + 1) % vertices.size])
    }.toTypedArray()

    operator fun contains(circle: Circle) = !edges.any { edge ->
        val dx = (circle.x - edge.normal.x * circle.radius - edge.center.x) * edge.normal.x
        val dy = (circle.y - edge.normal.y * circle.radius - edge.center.y) * edge.normal.y
        dx + dy < 0.0
    }
}
