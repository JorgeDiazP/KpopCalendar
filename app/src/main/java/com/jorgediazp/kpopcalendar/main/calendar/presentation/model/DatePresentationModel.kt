package com.jorgediazp.kpopcalendar.main.calendar.presentation.model

data class DatePresentationModel(
    val date: String,
    val isToday: Boolean,
    val songList: List<SongPresentationModel>
)
