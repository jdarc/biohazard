package demo.packman

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.Timer
import kotlin.system.exitProcess

internal class MainFrame : JFrame("Genetic Algorithms: Packman") {
    private val controller = Controller()
    private val surface = Visualizer()

    init {
        font = Font("Space Mono", Font.PLAIN, 9)
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()
        contentPane.add(surface, BorderLayout.CENTER)
        pack()
        setLocationRelativeTo(null)

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ESCAPE) exitProcess(0)
                if (e.keyCode == KeyEvent.VK_R) controller.reset()
            }
        })

        addComponentListener(object : ComponentAdapter() {
            override fun componentShown(e: ComponentEvent) {
                Thread {
                    while (true) {
                        controller.evolve()
                        Thread.yield()
                    }
                }.start()
                Timer(66) { render() }.start()
            }
        })
    }

    private fun render() {
        surface.clear()

        surface.setColor(Color.RED)
        for (edge in controller.shape.edges) {
            surface.drawLine(edge.p1.x, edge.p1.y, edge.p2.x, edge.p2.y)
        }
        surface.setColor(Color.BLUE)
        for (edge in controller.shape.edges) {
            surface.drawLine(edge.center.x, edge.center.y, edge.center.x - edge.normal.x, edge.center.y - edge.normal.y)
        }

        surface.setColor(Color.GREEN)
        controller.champion.forEach { surface.drawCircle(it.x, it.y, it.radius) }

        surface.font = font
        surface.setColor(Color.BLACK)
        surface.drawString("Generation: ${controller.generation}", 10, 20)
        surface.drawString("Fitness: ${controller.fitness}", 10, 32)

        surface.repaint()
    }
}
