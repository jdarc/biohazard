package demo

import java.awt.geom.Point2D
import java.util.concurrent.ThreadLocalRandom

internal object ConvexHull {

    private val RNG = ThreadLocalRandom.current()

    fun generate(count: Int): Array<Point2D.Double> {
        var rightMost = 0
        var highestXCoord = Double.NEGATIVE_INFINITY
        val vertices = Array(count) { Point2D.Double(RNG.nextDouble(-1.0, 1.0), RNG.nextDouble(-1.0, 1.0)) }
        vertices.forEachIndexed { index, vertex ->
            if (vertex.x > highestXCoord || vertex.x == highestXCoord && vertex.y < vertices[rightMost].y) {
                highestXCoord = vertex.x
                rightMost = index
            }
        }

        var outCount = 0
        var indexHull = rightMost
        val hull = IntArray(count)
        do {
            hull[outCount] = indexHull
            var nextHullIndex = 0
            val poc = vertices[hull[outCount]]
            for (i in vertices.indices) {
                if (nextHullIndex != indexHull) {
                    val e1x = vertices[nextHullIndex].x - poc.x
                    val e1y = vertices[nextHullIndex].y - poc.y
                    val e2x = vertices[i].x - poc.x
                    val e2y = vertices[i].y - poc.y
                    val c = e1x * e2y - e1y * e2x
                    if (c < 0.0 || c == 0.0 && e2x * e2x + e2y * e2y > e1x * e1x + e1y * e1y) {
                        nextHullIndex = i
                    }
                } else {
                    nextHullIndex = i
                }
            }
            ++outCount
            indexHull = nextHullIndex
        } while (indexHull != rightMost)

        return Array(outCount) { vertices[hull[it]] }
    }
}
