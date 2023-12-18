package com.jorgediazp.kpopcalendar.home.common.presentation.model

import com.jorgediazp.kpopcalendar.common.util.YoutubeUtils.Companion.getCompleteYoutubeUrlOrNull
import com.jorgediazp.kpopcalendar.common.util.YoutubeUtils.Companion.getYoutubeThumbnailUrlOrNull
import com.jorgediazp.kpopcalendar.common.util.YoutubeUtils.Companion.isValidYoutubeUrl
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel

class PresentationModelExtensions {

    companion object {

        fun SongDomainModel.toPresentationModel(isOddRow: Boolean): SongPresentationModel {
            val type =
                if (isValidYoutubeUrl(musicVideo)) SongPresentationType.RELEASED
                else if (isValidYoutubeUrl(teaserVideo)) SongPresentationType.TEASER
                else SongPresentationType.INFO
            val artist =
                artist ?: artists?.joinToString() ?: throw IllegalStateException("artist is null")
            var text = artist
            var youtubeUrl: String? = null
            var thumbnailUrl: String? = null
            when (type) {
                SongPresentationType.RELEASED -> {
                    if (titleTrack != null) {
                        text = "$artist - $titleTrack"
                    }
                    youtubeUrl = getCompleteYoutubeUrlOrNull(musicVideo)
                    thumbnailUrl = getYoutubeThumbnailUrlOrNull(musicVideo)
                }

                SongPresentationType.TEASER -> {
                    if (titleTrack != null) {
                        text = "$artist - $titleTrack (Teaser)"
                    }
                    youtubeUrl = getCompleteYoutubeUrlOrNull(teaserVideo)
                    thumbnailUrl = getYoutubeThumbnailUrlOrNull(teaserVideo)
                }

                SongPresentationType.INFO -> {
                    if (titleTrack != null) {
                        text = "$artist - $titleTrack"
                    }
                }
            }

            return SongPresentationModel(
                type = type,
                text = text,
                youtubeUrl = youtubeUrl,
                thumbnailUrl = thumbnailUrl,
                isOddRow = isOddRow
            )
        }
    }
}