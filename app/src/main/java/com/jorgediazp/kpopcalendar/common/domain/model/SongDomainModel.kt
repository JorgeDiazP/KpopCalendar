package com.jorgediazp.kpopcalendar.common.domain.model

data class SongDomainModel(
    val id: Int,
    val artist: String?,
    val artists: List<String>?,
    val titleTrack: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)
