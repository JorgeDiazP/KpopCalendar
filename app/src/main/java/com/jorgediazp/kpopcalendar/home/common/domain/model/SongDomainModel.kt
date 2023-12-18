package com.jorgediazp.kpopcalendar.home.common.domain.model

data class SongDomainModel(
    val id: Long,
    val artist: String?,
    val artists: List<String>?,
    val titleTrack: String?,
    val bSide: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)
