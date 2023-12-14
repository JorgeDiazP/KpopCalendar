package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel

data class DatePresentationModel(
    val date: String,
    val isSelectedDate: Boolean,
    val isToday: Boolean,
    val songList: List<SongPresentationModel>
)
