package demo.rostering

import com.zynaps.biohazard.Builder
import com.zynaps.biohazard.selectors.RouletteSelection
import demo.rostering.Generator.makeArchetype
import org.joda.time.format.DateTimeFormat

object Program {
    fun main() {
        val schedule = makeArchetype() // Generator.makeRandom(64, 20, 11);
        val mapper = Mapper(schedule)
        val island = Builder()
            .capacity(100)
            .genes(mapper.genomeLength)
            .selectionScheme(RouletteSelection(0.75))
            .crossoverRate(0.5)
            .mutationRate(0.0005)
            .nuke(true)
            .build()

        println(output(Controller(schedule, island, mapper, Rules(schedule)).evolve(60)))
    }

    private fun output(solution: Collection<Assignment>): String {
        val fullFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
        val shortTimeFormat = DateTimeFormat.forPattern("HH:mm")

        val grouped = solution.groupBy { it.shift }
        val shifts = grouped.keys.sortedBy { it.start.millis }

        val sb = StringBuilder()
        sb.appendLine()
        shifts.forEach { shift ->
            val start = shift.start.toString(fullFormat)
            val finish = shift.end.toString(fullFormat)
            sb.appendLine("Shift: ${shift.id}, Requires: ${shift.required}, Start: $start, End: $finish")
            grouped[shift]!!.sortedBy { it.employee.id }.forEach { assignment ->
                sb.appendLine("\tEmployee: ${assignment.employee.id}")
                assignment.employee.absences.forEach { absence ->
                    sb.appendLine(
                        "\t\tAbsent: ${absence.start.toString(fullFormat)} to ${
                            absence.end.toString(
                                shortTimeFormat
                            )
                        }"
                    )
                }
            }
            sb.appendLine()
        }
        return sb.toString()
    }
}
