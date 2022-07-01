package demo.worms

import demo.worms.world.Grid
import java.awt.Frame
import javax.swing.JFrame
import javax.swing.WindowConstants

internal class MainFrame : JFrame("Genetic Programming: Worms") {

    private val view = GridPanel()
    private val controller = Controller(::notify)

    init {
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.add(view)
        this.pack()

        this.isResizable = false
        this.setLocationRelativeTo(null)
        this.isVisible = true

        this.addWindowStateListener { event ->
            controller.replayChampion = event.newState and Frame.ICONIFIED != Frame.ICONIFIED
        }

        controller.start()
    }

    private fun notify(generation: Int, fitness: Double, maxFitness: Double, grid: Grid) =
        view.update(generation, fitness, maxFitness) { image ->
            grid.drawToBitmap(image)
        }
}
