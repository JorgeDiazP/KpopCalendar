package com.jorgediazp.kpopcalendar.home.common.presentation.model

data class SongPresentationModel(
    val id: Int,
    val type: SongPresentationType,
    val text: String,
    val youtubeUrl: String?,
    val thumbnailUrl: String?,
    val isOddRow: Boolean,
    val liked: Boolean
)
