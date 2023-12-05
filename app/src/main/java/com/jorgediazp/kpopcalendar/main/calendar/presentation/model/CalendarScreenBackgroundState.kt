package com.jorgediazp.kpopcalendar.main.calendar.presentation.model

sealed class CalendarScreenBackgroundState {

    data object ShowNothing : CalendarScreenBackgroundState()

    data class ShowDateList(val topBarTitle: String, val dateList: List<DatePresentationModel>) :
        CalendarScreenBackgroundState()

    data object ShowError : CalendarScreenBackgroundState()
}
