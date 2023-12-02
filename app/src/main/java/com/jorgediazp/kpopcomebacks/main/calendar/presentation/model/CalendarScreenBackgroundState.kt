package com.jorgediazp.kpopcomebacks.main.calendar.presentation.model

sealed class CalendarScreenBackgroundState {

    data object ShowNothing : CalendarScreenBackgroundState()

    class ShowSongList(val comebackMap: Map<String, List<ComebackVO>>) : CalendarScreenBackgroundState()
}
