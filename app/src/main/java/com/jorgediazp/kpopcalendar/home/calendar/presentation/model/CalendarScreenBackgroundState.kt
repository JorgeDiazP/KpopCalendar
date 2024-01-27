package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import java.time.Instant

sealed class CalendarScreenBackgroundState(
    open val topBarTitle: String,
    open val todayDayString: String,
    open val selectedDateMillis: Long
) {

    data object ShowNothing : CalendarScreenBackgroundState(
        topBarTitle = "",
        todayDayString = "",
        selectedDateMillis = Instant.now().toEpochMilli()
    )

    data class ShowDateList(
        override val topBarTitle: String,
        override val todayDayString: String,
        override val selectedDateMillis: Long,
        val selectedDateIndex: Int,
        val dateList: List<DatePresentationModel>,
        val songDomainIndexedMap: Map<Int, SongDomainModel>,
        val update: Int = 0 // This is a patch because I don't understand StateFlow. Stateflow change is triggered when the new object is not equal to the previous one.
    ) :
        CalendarScreenBackgroundState(
            topBarTitle = topBarTitle,
            todayDayString = todayDayString,
            selectedDateMillis = selectedDateMillis
        )

    class ShowError(
        topBarTitle: String,
        todayDayString: String,
        selectedDateMillis: Long
    ) :
        CalendarScreenBackgroundState(
            topBarTitle = topBarTitle,
            todayDayString = todayDayString,
            selectedDateMillis = selectedDateMillis
        )
}
