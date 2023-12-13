package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

import java.time.Instant

sealed class CalendarScreenBackgroundState(
    val topBarTitle: String,
    val todayDayString: String,
    val selectedDateMillis: Long
) {

    data object ShowNothing : CalendarScreenBackgroundState(
        topBarTitle = "",
        todayDayString = "",
        selectedDateMillis = Instant.now().toEpochMilli()
    )

    class ShowDateList(
        topBarTitle: String,
        todayDayString: String,
        selectedDateMillis: Long,
        val selectedDateIndex: Int,
        val dateList: List<DatePresentationModel>
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
