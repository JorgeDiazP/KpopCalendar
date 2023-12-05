package com.jorgediazp.kpopcalendar.main.calendar.presentation.model

sealed class CalendarScreenBackgroundState {

    data object ShowNothing : CalendarScreenBackgroundState()

    data class ShowSongList(val topBarTitle: String, val songMap: Map<String, List<SongPresentationModel>>) : CalendarScreenBackgroundState()
}
