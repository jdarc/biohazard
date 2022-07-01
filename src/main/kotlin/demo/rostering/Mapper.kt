package demo.rostering

import com.zynaps.biohazard.Creature
import demo.Helpers.pack
import kotlin.math.floor
import kotlin.math.ln

internal class Mapper(private val schedule: Schedule) {

    private val allocations = schedule.shifts.values.sumOf { it.required }

    private val shiftBits = bitsNeeded(schedule.shifts.values.maxOf { it.id })

    private val employeeBits = bitsNeeded(schedule.employees.values.maxOf { it.id })

    val genomeLength = allocations * (shiftBits + employeeBits)

    fun decode(creature: Creature) = Array(allocations) {
        val strand = pack(creature.chromosome, shiftBits + employeeBits, it * (shiftBits + employeeBits))
        val shift = schedule.shiftById((strand ushr employeeBits and (1 shl shiftBits) - 1L).toInt())
        val employee = schedule.employeeById((strand and (1 shl employeeBits) - 1L).toInt())
        if (shift != Shift.NONE && employee != Employee.NONE) {
            Assignment(shift, employee)
        } else {
            Assignment.NONE
        }
    }.filter { it != Assignment.NONE }.toList()

    private companion object {
        fun bitsNeeded(a: Int) = (floor(ln(a.toDouble()) / ln(2.0)) + 1.0).toInt()
    }
}
