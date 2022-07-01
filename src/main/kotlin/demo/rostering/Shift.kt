package demo.rostering

import org.joda.time.DateTime

internal class Shift(val id: Int, val start: DateTime, val end: DateTime, val required: Int) {

    companion object {
        val NONE = Shift(-1, DateTime(0), DateTime(0), 0)
    }
}