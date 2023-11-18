package com.jorgediazp.kpopcomebacks.main.data

import com.jorgediazp.kpopcomebacks.main.data.remote.model.ComebackRemoteDTO
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity

class ComebackExtensions {

    companion object {

        fun ComebackRemoteDTO.mapToEntity(): ComebackEntity {
            val artist = this.artist ?: ""
            return ComebackEntity(
                artist = artist,
                titleTrack = titleTrack,
                musicVideo = musicVideo,
                album = album,
                ost = ost,
                teaserPoster = teaserPoster,
                teaserVideo = teaserVideo
            )
        }
    }
}