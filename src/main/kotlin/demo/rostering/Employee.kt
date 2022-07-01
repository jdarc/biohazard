package demo.rostering

import org.joda.time.Interval

internal class Employee(val id: Int, vararg absences: Absence) {

    val absences = absences.asList()

    fun isAbsentDuring(shift: Shift) = Interval(shift.start, shift.end).run { absences.any { it.overlaps(this) } }

    companion object {
        val NONE = Employee(-1)
    }
}