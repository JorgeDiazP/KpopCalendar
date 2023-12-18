package com.jorgediazp.kpopcalendar.home.common.data.liked

import com.jorgediazp.kpopcalendar.common.util.MoshiUtils.Companion.getJsonFromObject
import com.jorgediazp.kpopcalendar.common.util.MoshiUtils.Companion.getObjectFromJson
import com.jorgediazp.kpopcalendar.home.common.data.liked.local.model.LikedSongDataModel
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.squareup.moshi.Types

class LikedSongsDataModelExtensions {

    companion object {

        fun SongDomainModel.toLikedDataLocalModel(): LikedSongDataModel {
            return LikedSongDataModel(
                id = id,
                artist = artist,
                artists = artists.getJsonFromObject(
                    Types.newParameterizedType(
                        List::class.java,
                        String::class.java
                    )
                ),
                titleTrack = titleTrack,
                bSide = bSide,
                musicVideo = musicVideo,
                album = album,
                ost = ost,
                teaserPoster = teaserPoster,
                teaserVideo = teaserVideo
            )
        }

        fun LikedSongDataModel.toDomainModel(): SongDomainModel {
            return SongDomainModel(
                id = id,
                artist = artist,
                artists = artists?.getObjectFromJson(
                    Types.newParameterizedType(
                        List::class.java,
                        String::class.java
                    )
                ),
                titleTrack = titleTrack,
                bSide = bSide,
                musicVideo = musicVideo,
                album = album,
                ost = ost,
                teaserPoster = teaserPoster,
                teaserVideo = teaserVideo
            )
        }
    }
}