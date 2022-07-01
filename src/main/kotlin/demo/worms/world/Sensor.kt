package demo.worms.world

internal fun interface Sensor {
    fun accept(grid: Grid, x: Int, y: Int): Boolean
}
