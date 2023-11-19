package com.jorgediazp.kpopcomebacks.main.data.remote.model

data class ComebackRemoteDTO(
    val artist: String? = null,
    val artists: List<String>? = null,
    val titleTrack: String? = null,
    val musicVideo: String? = null,
    val album: String? = null,
    val ost: String? = null,
    val teaserPoster: String? = null,
    val teaserVideo: String? = null
)
