package com.jorgediazp.kpopcalendar.home.common.presentation.model

import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationType

data class SongPresentationModel(
    val type: SongPresentationType,
    val text: String,
    val youtubeUrl: String?,
    val thumbnailUrl: String?,
    val isOddRow: Boolean
)
