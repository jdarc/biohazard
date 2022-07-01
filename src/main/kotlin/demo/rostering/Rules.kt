package demo.rostering

import org.joda.time.Hours
import kotlin.math.max
import kotlin.math.min

internal class Rules(schedule: Schedule) {
    private val shifts = schedule.shifts.values
    private val employees = schedule.employees.values
    private val restTime = schedule.restTime

    fun evaluate(assignments: Iterable<Assignment>) = processShiftRules(assignments) + processEmployeeRules(assignments)

    private fun processShiftRules(assignments: Iterable<Assignment>): Double {
        var fitness = 0.0
        for (shift in shifts) {
            val allocated = assignments.filter { it.shift.id == shift.id }.map { it.employee }
            if (allocated.isNotEmpty()) {
                val duplicates = allocated.map { it.id }.toSet().size != allocated.size
                if (duplicates) fitness -= HARD_SCORE
                for (employee in allocated) if (employee.isAbsentDuring(shift)) fitness -= HARD_SCORE
                fitness += (min(allocated.size, shift.required) - max(0, allocated.size - shift.required)).toDouble()
            } else {
                fitness -= shift.required.toDouble()
            }
        }
        return fitness
    }

    private fun processEmployeeRules(assignments: Iterable<Assignment>): Double {
        var fitness = 0.0
        for (employee in employees) {
            val shifts = assignments.filter { it.employee.id == employee.id }.map { it.shift }.sortedBy { it.start }
            for (i in 1 until shifts.size) {
                if (Hours.hoursBetween(shifts[i - 1].end, shifts[i].start).hours < restTime) fitness -= HARD_SCORE
            }
            fitness += shifts.size.toDouble()
        }
        return fitness
    }

    companion object {
        private const val HARD_SCORE = 1000.0
    }
}