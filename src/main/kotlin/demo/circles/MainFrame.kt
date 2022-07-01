package demo.circles

import demo.Helpers.configureGraphics
import demo.Helpers.screenSize
import demo.Helpers.withColor
import java.awt.Color
import java.awt.Font
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.geom.Ellipse2D
import javax.swing.JFrame
import javax.swing.Timer
import kotlin.system.exitProcess

internal class MainFrame(private val controller: Controller) : JFrame("Genetic Algorithms: Circles") {

    private fun render() {
        if (bufferStrategy == null) {
            createBufferStrategy(2)
            return
        }

        val g = configureGraphics(bufferStrategy.drawGraphics)

        g.translate(insets.left, insets.top)
        g.clearRect(0, 0, contentPane.width, contentPane.height)

        (controller.staticCircles + controller.championCircle).forEachIndexed { idx, it ->
            val oval = Ellipse2D.Double(it.x - it.radius, it.y - it.radius, it.radius * 2.0, it.radius * 2.0)
            withColor(g, if (idx == controller.staticCircles.size) Color.RED else Color.ORANGE) { it.fill(oval) }
            withColor(g, Color(0, 0, 0, 128)) { g.draw(oval) }
        }

        withColor(g, Color.BLACK) {
            it.drawString("Generation: ${controller.generation}", 10, 20)
            it.drawString("Fitness: ${String.format("%.2f", controller.fitness)}", 10, 42)
        }

        g.dispose()

        bufferStrategy.show()
    }

    init {
        font = Font("Space Mono", Font.PLAIN, 17)
        background = Color(250, 250, 235)
        defaultCloseOperation = EXIT_ON_CLOSE
        extendedState = MAXIMIZED_BOTH
        preferredSize = screenSize(0.9)
        ignoreRepaint = true

        pack()
        setLocationRelativeTo(null)

        addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ESCAPE) exitProcess(0)
                if (e.keyCode == KeyEvent.VK_R) controller.reset(contentPane.width, contentPane.height)
            }
        })

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) = controller.reset(contentPane.width, contentPane.height)
            override fun componentShown(e: ComponentEvent) {
                Timer(0) { controller.evolve() }.start()
                Timer(33) { render() }.start()
            }
        })
    }
}
