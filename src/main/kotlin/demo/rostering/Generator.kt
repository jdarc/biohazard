package demo.rostering

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

internal object Generator {
    private val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

//    fun makeRandom(totalShifts: Int, totalEmployees: Int, restTime: Int): Schedule {
//        val shifts: MutableList<Shift> = ArrayList(totalShifts)
//        val employees: MutableList<Employee> = ArrayList(totalEmployees)
//        val startOfWeek = LocalDate().withDayOfWeek(DateTimeConstants.MONDAY).toDateTimeAtStartOfDay()
//        for (i in 0 until totalShifts) {
//            val start = startOfWeek.plusDays((Math.random() * 7).toInt()).plusHours((Math.random() * 8).toInt())
//            shifts.add(Shift(i + 1, start, start.plusHours(8), (1 + Math.random() * 3).toInt()))
//        }
//        for (i in 0 until totalEmployees) {
//            var total = (0 + Math.random() * 3).toInt()
//            val absences: MutableList<Absence> = ArrayList(total)
//            while (total-- > 0) {
//                val hours = (Math.random() * 8).toInt()
//                val start = startOfWeek.plusDays((Math.random() * 7).toInt()).plusHours(hours)
//                absences.add(Absence(start, start.plusHours(1 + (Math.random() * (23 - hours)).toInt())))
//            }
//            employees.add(create(i + 1, absences))
//        }
//        return Schedule(shifts, employees, restTime)
//    }

    fun makeArchetype(): Schedule {
        val shifts = ArrayList<Shift>()
        shifts.add(Shift(1, parse("2015-07-13 06:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(2, parse("2015-07-13 06:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(3, parse("2015-07-13 06:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(4, parse("2015-07-13 07:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(5, parse("2015-07-13 07:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(6, parse("2015-07-13 07:30:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(7, parse("2015-07-13 09:00:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(8, parse("2015-07-13 09:00:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(9, parse("2015-07-13 09:00:00"), parse("2015-07-13 14:00:00"), 1))
        shifts.add(Shift(10, parse("2015-07-13 14:00:00"), parse("2015-07-13 19:00:00"), 1))
        shifts.add(Shift(11, parse("2015-07-13 14:00:00"), parse("2015-07-13 19:00:00"), 1))
        shifts.add(Shift(12, parse("2015-07-13 14:00:00"), parse("2015-07-13 19:00:00"), 1))
        shifts.add(Shift(13, parse("2015-07-13 16:45:00"), parse("2015-07-13 22:00:00"), 1))
        shifts.add(Shift(14, parse("2015-07-13 16:45:00"), parse("2015-07-13 22:00:00"), 1))
        shifts.add(Shift(15, parse("2015-07-13 16:45:00"), parse("2015-07-13 22:00:00"), 1))
        shifts.add(Shift(16, parse("2015-07-14 06:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(17, parse("2015-07-14 06:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(18, parse("2015-07-14 06:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(19, parse("2015-07-14 07:30:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(20, parse("2015-07-14 07:30:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(21, parse("2015-07-14 07:30:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(22, parse("2015-07-14 09:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(23, parse("2015-07-14 09:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(24, parse("2015-07-14 09:00:00"), parse("2015-07-14 14:00:00"), 1))
        shifts.add(Shift(25, parse("2015-07-14 14:00:00"), parse("2015-07-14 19:00:00"), 1))
        shifts.add(Shift(26, parse("2015-07-14 14:00:00"), parse("2015-07-14 19:00:00"), 1))
        shifts.add(Shift(27, parse("2015-07-14 14:00:00"), parse("2015-07-14 19:00:00"), 1))
        shifts.add(Shift(28, parse("2015-07-14 16:45:00"), parse("2015-07-14 22:00:00"), 1))
        shifts.add(Shift(29, parse("2015-07-14 16:45:00"), parse("2015-07-14 22:00:00"), 1))
        shifts.add(Shift(30, parse("2015-07-14 16:45:00"), parse("2015-07-14 22:00:00"), 1))
        shifts.add(Shift(31, parse("2015-07-14 22:00:00"), parse("2015-07-15 06:30:00"), 2))
        shifts.add(Shift(32, parse("2015-07-15 06:30:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(33, parse("2015-07-15 06:30:00"), parse("2015-07-15 14:00:00"), 2))
        shifts.add(Shift(34, parse("2015-07-15 06:30:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(35, parse("2015-07-15 07:30:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(36, parse("2015-07-15 07:30:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(37, parse("2015-07-15 07:30:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(38, parse("2015-07-15 09:00:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(39, parse("2015-07-15 09:00:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(40, parse("2015-07-15 09:00:00"), parse("2015-07-15 14:00:00"), 1))
        shifts.add(Shift(41, parse("2015-07-15 14:00:00"), parse("2015-07-15 19:00:00"), 1))
        shifts.add(Shift(42, parse("2015-07-15 14:00:00"), parse("2015-07-15 19:00:00"), 1))
        shifts.add(Shift(43, parse("2015-07-15 14:00:00"), parse("2015-07-15 19:00:00"), 1))
        shifts.add(Shift(44, parse("2015-07-15 16:45:00"), parse("2015-07-15 22:00:00"), 1))
        shifts.add(Shift(45, parse("2015-07-15 16:45:00"), parse("2015-07-15 22:00:00"), 1))
        shifts.add(Shift(46, parse("2015-07-15 16:45:00"), parse("2015-07-15 22:00:00"), 1))
        shifts.add(Shift(47, parse("2015-07-15 22:00:00"), parse("2015-07-16 06:30:00"), 2))
        shifts.add(Shift(48, parse("2015-07-16 06:30:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(49, parse("2015-07-16 06:30:00"), parse("2015-07-16 14:00:00"), 2))
        shifts.add(Shift(50, parse("2015-07-16 06:30:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(51, parse("2015-07-16 07:30:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(52, parse("2015-07-16 07:30:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(53, parse("2015-07-16 07:30:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(54, parse("2015-07-16 09:00:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(55, parse("2015-07-16 09:00:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(56, parse("2015-07-16 09:00:00"), parse("2015-07-16 14:00:00"), 1))
        shifts.add(Shift(57, parse("2015-07-16 14:00:00"), parse("2015-07-16 19:00:00"), 1))
        shifts.add(Shift(58, parse("2015-07-16 14:00:00"), parse("2015-07-16 19:00:00"), 1))
        shifts.add(Shift(59, parse("2015-07-16 14:00:00"), parse("2015-07-16 19:00:00"), 1))
        shifts.add(Shift(60, parse("2015-07-16 16:45:00"), parse("2015-07-16 22:00:00"), 1))
        shifts.add(Shift(61, parse("2015-07-16 16:45:00"), parse("2015-07-16 22:00:00"), 1))
        shifts.add(Shift(62, parse("2015-07-16 16:45:00"), parse("2015-07-16 22:00:00"), 1))
        shifts.add(Shift(63, parse("2015-07-16 22:00:00"), parse("2015-07-17 06:30:00"), 2))
        shifts.add(Shift(64, parse("2015-07-17 06:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(65, parse("2015-07-17 06:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(66, parse("2015-07-17 06:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(67, parse("2015-07-17 07:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(68, parse("2015-07-17 07:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(69, parse("2015-07-17 07:30:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(70, parse("2015-07-17 09:00:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(71, parse("2015-07-17 09:00:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(72, parse("2015-07-17 09:00:00"), parse("2015-07-17 14:00:00"), 1))
        shifts.add(Shift(73, parse("2015-07-17 14:00:00"), parse("2015-07-17 19:00:00"), 1))
        shifts.add(Shift(74, parse("2015-07-17 14:00:00"), parse("2015-07-17 19:00:00"), 1))
        shifts.add(Shift(75, parse("2015-07-17 14:00:00"), parse("2015-07-17 19:00:00"), 1))
        shifts.add(Shift(76, parse("2015-07-17 16:45:00"), parse("2015-07-17 22:00:00"), 1))
        shifts.add(Shift(77, parse("2015-07-17 16:45:00"), parse("2015-07-17 22:00:00"), 1))
        shifts.add(Shift(78, parse("2015-07-17 16:45:00"), parse("2015-07-17 22:00:00"), 1))
        shifts.add(Shift(79, parse("2015-07-17 14:00:00"), parse("2015-07-17 22:30:00"), 2))
        shifts.add(Shift(80, parse("2015-07-18 06:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(81, parse("2015-07-18 06:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(82, parse("2015-07-18 06:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(83, parse("2015-07-18 07:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(84, parse("2015-07-18 07:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(85, parse("2015-07-18 07:30:00"), parse("2015-07-18 14:00:00"), 1))
        shifts.add(Shift(86, parse("2015-07-18 09:00:00"), parse("2015-07-18 15:00:00"), 1))
        shifts.add(Shift(87, parse("2015-07-18 09:00:00"), parse("2015-07-18 15:00:00"), 1))
        shifts.add(Shift(88, parse("2015-07-18 09:00:00"), parse("2015-07-18 15:00:00"), 1))
        shifts.add(Shift(89, parse("2015-07-18 14:00:00"), parse("2015-07-18 19:00:00"), 1))
        shifts.add(Shift(90, parse("2015-07-18 14:00:00"), parse("2015-07-18 19:00:00"), 1))
        shifts.add(Shift(91, parse("2015-07-18 14:00:00"), parse("2015-07-18 19:00:00"), 1))
        shifts.add(Shift(92, parse("2015-07-18 16:45:00"), parse("2015-07-18 22:00:00"), 1))
        shifts.add(Shift(93, parse("2015-07-18 16:45:00"), parse("2015-07-18 22:00:00"), 1))
        shifts.add(Shift(94, parse("2015-07-18 16:45:00"), parse("2015-07-18 22:00:00"), 1))
        shifts.add(Shift(95, parse("2015-07-18 14:00:00"), parse("2015-07-18 22:30:00"), 2))
        shifts.add(Shift(96, parse("2015-07-19 06:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(97, parse("2015-07-19 06:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(98, parse("2015-07-19 06:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(99, parse("2015-07-19 07:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(100, parse("2015-07-19 07:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(101, parse("2015-07-19 07:30:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(102, parse("2015-07-19 09:00:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(103, parse("2015-07-19 09:00:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(104, parse("2015-07-19 09:00:00"), parse("2015-07-19 14:00:00"), 1))
        shifts.add(Shift(105, parse("2015-07-19 14:00:00"), parse("2015-07-19 19:00:00"), 1))
        shifts.add(Shift(106, parse("2015-07-19 14:00:00"), parse("2015-07-19 19:00:00"), 1))
        shifts.add(Shift(107, parse("2015-07-19 14:00:00"), parse("2015-07-19 19:00:00"), 1))
        shifts.add(Shift(108, parse("2015-07-19 16:45:00"), parse("2015-07-19 22:00:00"), 1))
        shifts.add(Shift(109, parse("2015-07-19 16:45:00"), parse("2015-07-19 22:00:00"), 1))
        shifts.add(Shift(110, parse("2015-07-19 16:45:00"), parse("2015-07-19 22:00:00"), 1))
        shifts.add(Shift(111, parse("2015-07-19 14:00:00"), parse("2015-07-19 22:30:00"), 2))

        val employees = ArrayList<Employee>()
        employees.add(
            Employee(
                1,
                absence("2015-07-16 00:00:00", "2015-07-26 23:59:00"),
                absence("2015-07-16 06:00:00", "2015-07-24 23:00:00")
            )
        )
        employees.add(Employee(2, absence("2015-07-14 00:00:00", "2015-07-28 23:00:00")))
        employees.add(
            Employee(
                3,
                absence("2015-07-16 01:45:00", "2015-07-16 18:45:00"),
                absence("2015-07-18 00:00:00", "2015-07-18 20:00:00")
            )
        )
        employees.add(
            Employee(
                4,
                absence("2015-07-13 06:15:00", "2015-07-13 10:15:00"),
                absence("2015-07-14 06:15:00", "2015-07-14 10:15:00"),
                absence("2015-07-16 06:15:00", "2015-07-16 10:15:00"),
                absence("2015-07-15 06:15:00", "2015-07-15 10:15:00"),
                absence("2015-07-17 06:15:00", "2015-07-17 10:15:00"),
                absence("2015-07-18 06:15:00", "2015-07-18 10:15:00"),
                absence("2015-07-19 06:15:00", "2015-07-19 10:15:00")
            )
        )
        employees.add(Employee(5))
        employees.add(Employee(6, absence("2015-07-16 00:00:00", "2015-07-16 23:59:00")))
        employees.add(Employee(7))
        employees.add(Employee(8, absence("2015-07-18 00:00:00", "2015-07-26 23:59:00")))
        employees.add(Employee(9))
        employees.add(
            Employee(
                10,
                absence("2015-07-17 15:00:00", "2015-07-17 23:00:00"),
                absence("2015-07-18 15:00:00", "2015-07-18 23:00:00"),
                absence("2015-07-13 06:00:00", "2015-07-13 23:00:00"),
                absence("2015-07-19 13:00:00", "2015-07-19 23:00:00"),
                absence("2015-07-14 06:00:00", "2015-07-14 17:00:00"),
                absence("2015-07-15 06:00:00", "2015-07-15 17:00:00"),
                absence("2015-07-16 06:00:00", "2015-07-16 17:00:00")
            )
        )
        employees.add(Employee(11, absence("2015-07-11 00:00:00", "2015-07-13 23:59:00")))
        employees.add(Employee(12))
        employees.add(Employee(13, absence("2015-07-17 00:00:00", "2015-07-27 23:59:00")))
        employees.add(Employee(14))
        employees.add(Employee(15, absence("2015-07-18 00:00:00", "2015-07-18 23:59:00")))
        employees.add(Employee(16, absence("2015-07-13 00:00:00", "2015-07-13 14:00:00")))
        employees.add(Employee(17))
        employees.add(Employee(18))
        employees.add(
            Employee(
                19,
                absence("2015-07-14 18:00:00", "2015-07-14 22:00:00"),
                absence("2015-07-16 18:00:00", "2015-07-16 22:00:00"),
                absence("2015-07-15 00:00:00", "2015-07-15 23:59:00"),
                absence("2015-07-17 00:00:00", "2015-07-17 23:59:00")
            )
        )
        employees.add(Employee(20))
        employees.add(Employee(21))
        employees.add(
            Employee(
                22,
                absence("2015-07-13 06:45:00", "2015-07-13 14:00:00"),
                absence("2015-07-15 06:45:00", "2015-07-15 14:00:00"),
                absence("2015-07-16 16:00:00", "2015-07-16 23:00:00"),
                absence("2015-07-17 00:00:00", "2015-07-17 23:59:00"),
                absence("2015-07-18 00:00:00", "2015-07-18 23:59:00"),
                absence("2015-07-19 00:00:00", "2015-07-19 23:59:00")
            )
        )
        employees.add(Employee(23, absence("2015-07-13 22:00:00", "2015-07-14 02:00:00")))
        employees.add(Employee(24))
        employees.add(Employee(25, absence("2015-07-17 15:00:00", "2015-07-19 23:00:00")))
        employees.add(Employee(26))
        employees.add(
            Employee(
                27,
                absence("2015-06-23 07:00:00", "2015-09-06 23:00:00"),
                absence("2015-07-06 07:00:00", "2015-07-31 23:00:00"),
                absence("2015-07-13 07:00:00", "2015-07-19 23:00:00")
            )
        )
        employees.add(Employee(28))
        employees.add(Employee(29))
        employees.add(
            Employee(
                30,
                absence("2015-07-14 10:00:00", "2015-07-14 22:15:00"),
                absence("2015-07-17 10:00:00", "2015-07-17 14:00:00")
            )
        )
        employees.add(Employee(31))
        employees.add(Employee(32))
        employees.add(Employee(33, absence("2015-07-13 00:00:00", "2015-07-13 23:59:00")))
        employees.add(Employee(34))
        employees.add(Employee(35))
        employees.add(Employee(36, absence("2015-07-01 00:00:00", "2015-07-31 23:59:00")))
        employees.add(Employee(37))
        employees.add(Employee(38))
        employees.add(Employee(39, absence("2015-07-14 00:00:00", "2015-07-14 23:59:00")))
        employees.add(Employee(40))

        return Schedule(shifts, employees, 11)
    }

    private fun absence(start: String, end: String) = Absence(parse(start), parse(end))

    private fun parse(s: String) = DateTime.parse(s, formatter)
}