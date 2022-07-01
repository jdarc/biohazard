package demo.worms.world

internal class Antennae(private val op: Sensor) {
    var sw = 0
        private set

    var w = 0
        private set

    var nw = 0
        private set

    var n = 0
        private set

    var ne = 0
        private set

    var e = 0
        private set

    var se = 0
        private set

    fun sense(grid: Grid, x: Int, y: Int, orientation: Orientation) {
        val ndist = scan(grid, x, y, 0, -1)
        val sdist = scan(grid, x, y, 0, 1)
        val edist = scan(grid, x, y, 1, 0)
        val wdist = scan(grid, x, y, -1, 0)
        val nwdist = scan(grid, x, y, -1, -1)
        val nedist = scan(grid, x, y, 1, -1)
        val swdist = scan(grid, x, y, -1, 1)
        val sedist = scan(grid, x, y, 1, 1)

        when (orientation) {
            Orientation.EAST -> {
                sw = nwdist
                w = ndist
                nw = nedist
                n = edist
                ne = sedist
                e = sdist
                se = swdist
            }
            Orientation.SOUTH -> {
                sw = nedist
                w = edist
                nw = sedist
                n = sdist
                ne = swdist
                e = wdist
                se = swdist
            }
            Orientation.WEST -> {
                sw = sedist
                w = sdist
                nw = swdist
                n = wdist
                ne = nwdist
                e = ndist
                se = nedist
            }
            Orientation.NORTH -> {
                sw = swdist
                w = wdist
                nw = nwdist
                n = ndist
                ne = nedist
                e = edist
                se = sedist
            }
        }
    }

    private fun scan(grid: Grid, x: Int, y: Int, dx: Int, dy: Int): Int {
        var sx = x
        var sy = y
        var dist = 0
        while (op.accept(grid, sx.also { sx += dx }, sy.also { sy += dy })) ++dist
        return dist
    }
}
