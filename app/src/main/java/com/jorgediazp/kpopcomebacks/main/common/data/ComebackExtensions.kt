package com.jorgediazp.kpopcomebacks.main.common.data

import com.jorgediazp.kpopcomebacks.main.common.domain.ComebackEntity

class ComebackExtensions {

    companion object {

        const val COMEBACKS_COLLECTION = "comebacks"
        const val COMEBACKS_FIELD = "comebacks"
        const val DATE_FIELD = "date"

        const val ARTIST_FIELD = "artist"
        const val ARTISTS_FIELD = "artists"
        const val TITLE_TRACK_FIELD = "titleTrack"
        const val MUSIC_VIDEO_FIELD = "musicVideo"
        const val ALBUM_FIELD = "album"
        const val OST_FIELD = "ost"
        const val TEASER_POSTER_FIELD = "teaserPoster"
        const val TEASER_VIDEO_FIELD = "teaserVideo"

        fun HashMap<String, Any>.mapToComebackEntity(): ComebackEntity {
            return ComebackEntity(
                artist = this[ARTIST_FIELD] as String?
                    ?: (this[ARTISTS_FIELD] as List<String>?)?.joinToString()
                    ?: throw IllegalStateException("artist is null"),
                titleTrack = this[TITLE_TRACK_FIELD] as String?,
                musicVideo = this[MUSIC_VIDEO_FIELD] as String?,
                album = this[ALBUM_FIELD] as String?,
                ost = this[OST_FIELD] as String?,
                teaserPoster = this[TEASER_POSTER_FIELD] as String?,
                teaserVideo = this[TEASER_VIDEO_FIELD] as String?
            )
        }

        fun List<HashMap<String, Any>>.mapToComebackEntityList(): List<ComebackEntity> {
            return this.map { it.mapToComebackEntity() }
        }
    }
}