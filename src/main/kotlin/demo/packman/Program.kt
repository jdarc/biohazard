package demo.packman

import javax.swing.SwingUtilities

object Program {
    fun main() = SwingUtilities.invokeLater { MainFrame().isVisible = true }
}
