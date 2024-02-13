package com.jorgediazp.kpopcalendar.common.presentation.model

data class SongPresentationModel(
    val id: Int,
    val type: SongPresentationType,
    val text: String,
    val youtubeUrl: String?,
    val thumbnailUrl: String?,
    val isOddRow: Boolean,
    var liked: Boolean
)
