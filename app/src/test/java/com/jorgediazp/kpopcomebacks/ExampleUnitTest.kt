package com.jorgediazp.kpopcomebacks

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.GregorianCalendar

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val calendar = GregorianCalendar()
        calendar.set(2023, 11-1, 1)
        val daysInMonth = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("yyyyMMdd")
        val dayList = mutableListOf<String>()
        for (i in 1..daysInMonth) {
            dayList.add(sdf.format(calendar.time))
            calendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
        }
        println(dayList.toString())
        assertEquals(4, 2 + 2)
    }
}