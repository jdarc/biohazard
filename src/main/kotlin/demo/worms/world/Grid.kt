package demo.worms.world

import demo.Automata
import demo.Bitmap
import java.awt.image.BufferedImage

internal class Grid(private val size: Int) {
    private val pixels = Bitmap(size, size)
    private val trail = IntArray(TRAIL_LENGTH)
    private var trailIndex = 0

    fun isObstacle(x: Int, y: Int) = get(x, y) == WALL || get(x, y) == OBSTACLE

    fun isFood(x: Int, y: Int) = get(x, y) == FOOD

    operator fun get(x: Int, y: Int) = pixels[x, y]

    fun set(x: Int, y: Int) {
        trail[TRAIL_MASK and trailIndex++] = y * size + x
        pixels[trail[TRAIL_MASK and trailIndex]] = FREE
        pixels[x, y] = CREATURE
    }

    fun configure(automata: Automata) {
        for (x in 0 until automata.width) {
            for (y in 0 until automata.height) {
                pixels[x, y] = if (automata[x, y] == 1) OBSTACLE else FOOD
            }
        }

        for (p in 0 until size) {
            pixels[p] = WALL
            pixels[0xFF * size + p] = WALL
            pixels[p * size] = WALL
            pixels[p * size + 0xFF] = WALL
        }
        trailIndex = 0

        val center = (size shr 1) * size + (size shr 1)
        trail.fill(center)
    }

    fun drawToBitmap(image: BufferedImage) = image.setRGB(0, 0, size, size, pixels.data, 0, size)

    companion object {
        private const val FREE = 0x000000
        private const val FOOD = 0x000044
        private const val WALL = 0x0088FF
        private const val OBSTACLE = 0x0000FF
        private const val CREATURE = 0xFF0000
        private const val TRAIL_LENGTH = 16
        private const val TRAIL_MASK = 15
    }
}
