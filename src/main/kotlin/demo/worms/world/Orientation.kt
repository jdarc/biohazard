package demo.worms.world

internal enum class Orientation(val dx: Int, val dy: Int) {
    EAST(1, 0), SOUTH(0, 1), WEST(-1, 0), NORTH(0, -1);

    fun left() = values[0x03 and ordinal - 1]

    fun right() = values[0x03 and ordinal + 1]

    companion object {
        val values = values()
    }
}
