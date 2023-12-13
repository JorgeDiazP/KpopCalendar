package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

sealed class CalendarScreenForegroundState {

    data object ShowNothing : CalendarScreenForegroundState()

    data object ShowLoading : CalendarScreenForegroundState()

    data class ShowCalendarPicker(
        val initialSelectedDateMillis: Long,
        val yearRange: IntRange,
        val minTimestamp: Long,
        val maxTimestamp: Long
    ) : CalendarScreenForegroundState()
}
