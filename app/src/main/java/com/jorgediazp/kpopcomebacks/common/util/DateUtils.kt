package com.jorgediazp.kpopcomebacks.common.util

import java.text.SimpleDateFormat
import java.util.Date

class DateUtils {

    companion object {

        const val COMEBACK_DATE_FORMAT = "yyyyMMdd"

        fun Date.getStringFormat(format: String): String {
            return SimpleDateFormat(format).format(this)
        }

        fun List<Date>.getStringFormat(format: String): List<String> {
            return this.map { date -> date.getStringFormat(format) }
        }
    }
}