package demo.rostering

internal class Assignment(val shift: Shift, val employee: Employee) {
    companion object {
        val NONE = Assignment(Shift.NONE, Employee.NONE)
    }
}
