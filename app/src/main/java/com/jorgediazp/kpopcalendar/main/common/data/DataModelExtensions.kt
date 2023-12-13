package com.jorgediazp.kpopcalendar.main.common.data

import com.jorgediazp.kpopcalendar.main.common.domain.SongDomainModel

class DataModelExtensions {

    companion object {

        const val SONGS_COLLECTION = "songs"
        const val SONGS_FIELD = "songs"
        const val DATE_FIELD = "date"
        const val TAGS_FIELD = "tags"

        const val ARTIST_FIELD = "artist"
        const val ARTISTS_FIELD = "artists"
        const val TITLE_TRACK_FIELD = "titleTrack"
        const val MUSIC_VIDEO_FIELD = "musicVideo"
        const val ALBUM_FIELD = "album"
        const val OST_FIELD = "ost"
        const val TEASER_POSTER_FIELD = "teaserPoster"
        const val TEASER_VIDEO_FIELD = "teaserVideo"

        fun HashMap<String, Any>.toDomainModel(): SongDomainModel {
            return SongDomainModel(
                artist = this[ARTIST_FIELD] as String?,
                artists = this[ARTISTS_FIELD] as List<String>?,
                titleTrack = this[TITLE_TRACK_FIELD] as String?,
                musicVideo = this[MUSIC_VIDEO_FIELD] as String?,
                album = this[ALBUM_FIELD] as String?,
                ost = this[OST_FIELD] as String?,
                teaserPoster = this[TEASER_POSTER_FIELD] as String?,
                teaserVideo = this[TEASER_VIDEO_FIELD] as String?
            )
        }
    }
}