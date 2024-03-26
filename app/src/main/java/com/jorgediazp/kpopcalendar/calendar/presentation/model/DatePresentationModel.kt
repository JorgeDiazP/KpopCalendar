package com.jorgediazp.kpopcalendar.calendar.presentation.model

import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel

data class DatePresentationModel(
    val date: String,
    val isSelectedDate: Boolean,
    val isToday: Boolean,
    val songList: List<SongPresentationModel>
)
