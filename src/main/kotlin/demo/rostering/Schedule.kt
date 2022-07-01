package demo.rostering

internal class Schedule(shifts: List<Shift>, employees: List<Employee>, restTime: Int) {

    val shifts = shifts.associateBy { it.id }.toMap()

    val employees = employees.associateBy { it.id }

    val restTime = restTime.coerceAtLeast(0)

    fun shiftById(id: Int) = shifts.getOrDefault(id, Shift.NONE)

    fun employeeById(id: Int) = employees.getOrDefault(id, Employee.NONE)
}
