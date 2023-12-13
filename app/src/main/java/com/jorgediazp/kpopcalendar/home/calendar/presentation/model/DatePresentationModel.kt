package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

data class DatePresentationModel(
    val date: String,
    val isSelectedDate: Boolean,
    val isToday: Boolean,
    val songList: List<SongPresentationModel>
)
