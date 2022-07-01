package demo

import org.graalvm.polyglot.Context
import java.awt.*
import kotlin.math.ceil

internal object Helpers {

    fun sqr(x: Double) = x * x

    fun pack(chromosome: BooleanArray, size: Int, offset: Int = 0, every: Int = 1): Long {
        var result = 0L
        for (i in 0 until size) {
            result = result shl 1 or if (chromosome[offset + i * every]) 1 else 0
        }
        return result
    }

    fun configureGraphics(g: Graphics) = (g as Graphics2D).apply {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)
    }

    fun withColor(g: Graphics2D, c: Color, cb: (Graphics2D) -> Unit) {
        val old = g.color
        g.color = c
        cb(g)
        g.color = old
    }

    fun screenSize(scale: Double = 1.0) = Dimension(
        ceil(Toolkit.getDefaultToolkit().screenSize.width * scale.coerceIn(0.01, 1.0)).toInt(),
        ceil(Toolkit.getDefaultToolkit().screenSize.height * scale.coerceIn(0.01, 1.0)).toInt()
    )

    fun evalStringEq(s: String) = ctx.eval("js", s).toString().toDouble()

    private val ctx = Context.newBuilder("js").option("engine.WarnInterpreterOnly", "false").build()
}
