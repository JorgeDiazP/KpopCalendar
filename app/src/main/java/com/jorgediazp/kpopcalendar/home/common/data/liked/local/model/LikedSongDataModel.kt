package com.jorgediazp.kpopcalendar.home.common.data.liked.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikedSongs")
data class LikedSongDataModel(
    @PrimaryKey val id: Int,
    val artist: String?,
    val artists: String?,
    val titleTrack: String?,
    val bSide: String?,
    val musicVideo: String?,
    val album: String?,
    val ost: String?,
    val teaserPoster: String?,
    val teaserVideo: String?
)