package demo.worms

import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.JPanel

internal class GridPanel : JPanel() {
    private val image: BufferedImage
    private var generation = 0
    private var fitness = 0.0
    private var maxFitness = 0.0

    public override fun paintComponent(g: Graphics) {
        val gx = g as Graphics2D
        gx.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        gx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        gx.color = Color.BLACK
        gx.fillRect(0, 0, width, height)

        gx.drawImage(image, width - 512 shr 1, height - 512 shr 1, 512, 512, this)

        gx.color = Color.WHITE
        gx.drawString("Generation: $generation", 10, 20)
        gx.drawString("Fitness: $fitness", 10, 40)
        gx.drawString("Max Fitness: $maxFitness", 10, 584)
    }

    fun update(gen: Int, fit: Double, max: Double, op: (BufferedImage) -> Unit) {
        generation = gen
        fitness = fit
        maxFitness = max
        op(image)
        repaint()
    }

    init {
        font = Font("Space Mono", Font.PLAIN, 15)
        background = Color.BLACK
        foreground = Color.WHITE
        ignoreRepaint = true
        preferredSize = Dimension(800, 600)
        val dgc = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration
        image = dgc.createCompatibleImage(256, 256)
    }
}
