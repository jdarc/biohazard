package demo

class Bitmap(width: Int, height: Int) {

    val width = width.coerceIn(1, 4096)
    val height = width.coerceIn(1, 4096)

    val data = IntArray(width * height)

    val size = data.size

    operator fun get(index: Int) = data[index]

    operator fun get(x: Int, y: Int) = data[offset(x, y)]

    operator fun set(index: Int, value: Int) {
        data[index] = value
    }

    operator fun set(x: Int, y: Int, value: Int) {
        data[offset(x, y)] = value
    }

    private fun offset(x: Int, y: Int) = y * width + x
}
