package com.jorgediazp.kpopcalendar.common.data.liked.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikedSongs")
data class LikedSongDataModel(
    @PrimaryKey val id: Int,
    val dateMillis: Long,
    val artist: String?,
    val artists: String?,
    val titleTrack: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)