package com.jorgediazp.kpopcalendar.home.calendar.presentation.model

data class SongPresentationModel(
    val type: SongPresentationType,
    val text: String,
    val youtubeUrl: String?,
    val thumbnailUrl: String?,
    val isOddRow: Boolean
)
