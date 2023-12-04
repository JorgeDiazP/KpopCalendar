package com.jorgediazp.kpopcomebacks.common.util

import java.text.SimpleDateFormat
import java.util.Date

class DateUtils {

    companion object {

        const val COMEBACK_DATE_FORMAT = "yyyyMMdd"
        const val MONTH_DATE_FORMAT = "MMMM"
        const val MONTH_AND_YEAR_DATE_FORMAT = "MMMM yyyy"

        fun Date.getStringFormat(format: String): String {
            return SimpleDateFormat(format).format(this)
        }

        fun List<Date>.getStringFormat(format: String): List<String> {
            return this.map { date -> date.getStringFormat(format) }
        }
    }
}