package demo.packman

import demo.Helpers
import java.awt.*
import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.GeneralPath
import java.awt.geom.Line2D
import javax.swing.JPanel
import kotlin.math.max

internal class Visualizer : JPanel() {
    private val drawables = mutableListOf<Drawable>()
    private var color = Color.BLACK
    private val scale = 50.0

    override fun paint(g: Graphics) {
        val gx = configureGraphics(g)
        val scale = max(width, height) / (2.0 * scale)

        drawSmallGrid(gx, scale)
        drawNotches(gx, scale)
        drawAxis(gx, scale)

        drawables.forEach { it.draw(gx) }

        gx.transform = AffineTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0)
    }

    private fun configureGraphics(gx: Graphics) = Helpers.configureGraphics(gx).also {
        it.clearRect(0, 0, width, height)
        it.translate(width * 0.5, height * 0.5)
        it.scale(scale, -scale)
        it.stroke = BasicStroke((1.0 / scale).toFloat())
        it.font = font
    }

    private fun drawSmallGrid(gx: Graphics2D, e: Double) {
        var l = 1.0
        val p = GeneralPath()
        while (l < e) {
            p.moveTo(-l, e)
            p.lineTo(-l, -e)
            p.moveTo(l, e)
            p.lineTo(l, -e)
            p.moveTo(e, -l)
            p.lineTo(-e, -l)
            p.moveTo(e, l)
            p.lineTo(-e, l)
            l += 1.0
        }
        gx.color = Color(230, 230, 240)
        gx.draw(p)
    }

    private fun drawNotches(gx: Graphics2D, e: Double) {
        var l = 0.5
        val notch = 6.0 / scale
        val p = GeneralPath()
        while (l < e) {
            val notch1 = if (l != l.toInt().toDouble()) notch * 0.5 else notch
            p.moveTo(-l, notch1)
            p.lineTo(-l, -notch1)
            p.moveTo(l, notch1)
            p.lineTo(l, -notch1)
            p.moveTo(notch1, -l)
            p.lineTo(-notch1, -l)
            p.moveTo(notch1, l)
            p.lineTo(-notch1, l)
            l += 0.5
        }
        gx.color = Color(200, 200, 200)
        gx.draw(p)
    }

    private fun drawAxis(gx: Graphics2D, e: Double) {
        val p = GeneralPath()
        p.moveTo(0.0, -e)
        p.lineTo(0.0, e)
        p.moveTo(-e, 0.0)
        p.lineTo(e, 0.0)
        gx.color = Color(200, 200, 200)
        gx.draw(p)
    }

    fun clear() = drawables.clear()

    fun setColor(color: Color) {
        this.color = color
    }

    fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        addMarker(x1, y1)
        addMarker(x2, y2)
        addWire(Line2D.Double(x1, y1, x2, y2))
    }

    fun drawCircle(x: Double, y: Double, radius: Double) {
        setColor(Color(color.red, color.green, color.blue, 24))
        addSolid(Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2))
        setColor(Color(color.red, color.green, color.blue))
        addWire(Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2))
    }

    fun drawString(s: String, x: Int, y: Int) {
        drawables.add(Text(s, x, y))
    }

    private fun addMarker(x: Double, y: Double) {
        val offset = 3.0 / scale
        val size = 6.0 / scale
        addSolid(Ellipse2D.Double(x - offset, y - offset, size, size))
    }

    private fun addWire(shape: Shape) {
        drawables.add(Wire(shape, color))
    }

    private fun addSolid(shape: Shape) {
        drawables.add(Solid(shape, color))
    }

    init {
        background = Color(250, 250, 250)
        preferredSize = Dimension(1440, 900)
    }

    private companion object {

        private fun interface Drawable {
            fun draw(g: Graphics2D)
        }

        private class Text(private val msg: String, private val x: Int, private val y: Int) : Drawable {
            override fun draw(g: Graphics2D) {
                val tx = g.transform
                g.color = Color.black
                g.transform = AffineTransform(2.0, 0.0, 0.0, 2.0, 0.0, 0.0)
                g.drawString(msg, x, y)
                g.transform = tx
            }
        }

        private class Wire(private val shape: Shape, private val color: Color) : Drawable {
            override fun draw(g: Graphics2D) {
                g.color = color
                g.draw(shape)
            }
        }

        private class Solid(private val shape: Shape, private val color: Color) : Drawable {
            override fun draw(g: Graphics2D) {
                g.color = color
                g.fill(shape)
            }
        }
    }
}
