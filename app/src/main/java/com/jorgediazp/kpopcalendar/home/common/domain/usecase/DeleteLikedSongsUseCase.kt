package com.jorgediazp.kpopcalendar.home.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.LikedSongsRepositoryModule
import javax.inject.Inject

class DeleteLikedSongsUseCase @Inject constructor(
    @LikedSongsRepositoryModule.LikedSongsRepositoryQualifier private val repository: LikedSongsDataSource
) {

    suspend fun deleteLikedSong(songId: Int): DataResult<Nothing> {
        return repository.deleteLikedSong(songId)
    }
}