package com.jorgediazp.kpopcalendar.home.common.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Songs")
data class SongDataLocalModel(
    @PrimaryKey val id: Int,
    val artist: String?,
    val artists: List<String>?,
    val titleTrack: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)