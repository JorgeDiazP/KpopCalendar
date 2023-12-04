package com.jorgediazp.kpopcomebacks.common.util

class YoutubeUtils {

    companion object {
        const val VALID_YOUTUBE_URL_PATTERN =
            """^(https?://)?(www\.)?(youtube\.com/watch\?v=|youtu\.be/|youtube\.com/embed/|youtube\.com/v/|youtube\.com/user/.+/u/|\?v=)([^#\&\?\n]*).*"""

        fun isValidYoutubeUrl(url: String?): Boolean {
            return url != null && VALID_YOUTUBE_URL_PATTERN.toRegex().matches(url)
        }

        fun getCompleteYoutubeUrlOrNull(url: String?): String? {
            return if (isValidYoutubeUrl(url)) {
                val id = url?.substringAfter(".be/")?.substringBefore("?")
                "https://www.youtube.com/watch?v=${id}"
            } else {
                null
            }
        }

        fun getYoutubeThumbnailUrlOrNull(url: String?): String? {
            return if (isValidYoutubeUrl(url)) {
                val id = url?.substringAfter(".be/")?.substringBefore("?")
                "https://img.youtube.com/vi/${id}/0.jpg"
            } else {
                null
            }
        }
    }
}