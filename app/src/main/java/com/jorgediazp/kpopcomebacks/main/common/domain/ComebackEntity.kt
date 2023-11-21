package com.jorgediazp.kpopcomebacks.main.common.domain

data class ComebackEntity(
    val artist: String,
    val titleTrack: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)
