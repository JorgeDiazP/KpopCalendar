package com.jorgediazp.kpopcalendar.main.calendar.presentation.model

import java.time.Instant

sealed class CalendarScreenBackgroundState(
    val topBarTitle: String,
    val selectedDateMillis: Long
) {

    data object ShowNothing : CalendarScreenBackgroundState(
        topBarTitle = "",
        selectedDateMillis = Instant.now().toEpochMilli()
    )

    class ShowDateList(
        topBarTitle: String,
        selectedDateMillis: Long,
        val dateList: List<DatePresentationModel>
    ) :
        CalendarScreenBackgroundState(
            topBarTitle = topBarTitle,
            selectedDateMillis = selectedDateMillis
        )

    class ShowError(topBarTitle: String, selectedDateMillis: Long) :
        CalendarScreenBackgroundState(
            topBarTitle = topBarTitle,
            selectedDateMillis = selectedDateMillis
        )
}
