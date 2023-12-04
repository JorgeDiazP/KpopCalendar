package com.jorgediazp.kpopcomebacks.main.calendar.presentation.model

data class SongPresentationModel(
    val type: SongPresentationType,
    val text: String,
    val artist: String,
    val titleTrack: String?,
    val album: String?,
    val ost: String?,
    val youtubeUrl: String?,
    val thumbnailUrl: String?,
)
