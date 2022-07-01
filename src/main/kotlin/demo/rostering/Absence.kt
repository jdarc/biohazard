package demo.rostering

import org.joda.time.DateTime
import org.joda.time.Interval

internal class Absence(val start: DateTime, val end: DateTime, private val interval: Interval = Interval(start, end)) {
    fun overlaps(other: Interval) = interval.overlaps(other)
}
