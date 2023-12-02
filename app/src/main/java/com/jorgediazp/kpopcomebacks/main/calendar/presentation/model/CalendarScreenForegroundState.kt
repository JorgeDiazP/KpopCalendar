package com.jorgediazp.kpopcomebacks.main.calendar.presentation.model

sealed class CalendarScreenForegroundState {

    data object ShowNothing : CalendarScreenForegroundState()

    data object ShowLoading : CalendarScreenForegroundState()

    data class ShowCalendarPicker(val minTimestamp: Long, val maxTimestamp: Long) : CalendarScreenForegroundState()
}
