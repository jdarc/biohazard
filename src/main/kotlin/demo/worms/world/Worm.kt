package demo.worms.world

import kotlin.math.max
import kotlin.math.min

internal class Worm {

    var x = 0
        private set

    var y = 0
        private set

    var energy = 100
        private set

    var orientation = Orientation.NORTH
        private set

    val wallAntennae = Antennae(wallScanner)

    val foodAntennae = Antennae(foodScanner)

    val isAlive get() = energy > 0

    fun useEnergy(amount: Int) {
        energy = max(0, energy - amount)
    }

    fun addEnergy(amount: Int) {
        energy = min(300, energy + amount)
    }

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun turnLeft() {
        orientation = orientation.left()
    }

    fun turnRight() {
        orientation = orientation.right()
    }

    fun moveForward() {
        x += orientation.dx
        y += orientation.dy
    }

    fun reset() {
        energy = 100
        orientation = Orientation.NORTH
    }

    fun kill() {
        energy = 0
    }

    fun sense(grid: Grid) {
        wallAntennae.sense(grid, x, y, orientation)
        foodAntennae.sense(grid, x, y, orientation)
    }

    private companion object {
        val wallScanner: (grid: Grid, x: Int, y: Int) -> Boolean = { grid, x, y -> !grid.isObstacle(x, y) }
        val foodScanner: (grid: Grid, x: Int, y: Int) -> Boolean =
            { grid, x, y -> !grid.isObstacle(x, y) && !grid.isFood(x, y) }
    }
}
