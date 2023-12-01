package com.jorgediazp.kpopcomebacks.main.calendar.presentation.model

sealed class CalendarState {

    class ShowSongList(val comebackMap: Map<String, List<ComebackVO>>) : CalendarState()

    data object ShowCalendarPicker : CalendarState()
}
