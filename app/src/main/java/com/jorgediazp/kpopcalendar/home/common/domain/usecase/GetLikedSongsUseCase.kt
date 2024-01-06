package com.jorgediazp.kpopcalendar.home.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.LikedSongsRepositoryModule
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedSongsUseCase @Inject constructor(
    @LikedSongsRepositoryModule.LikedSongsRepositoryQualifier private val repository: LikedSongsDataSource
) {

    suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>> {
        return repository.getAllLikedSongs()
    }

    suspend fun getAllLikedSongIdsFlow(): Flow<List<Int>> {
        return repository.getAllLikedSongIdsFlow()
    }

    suspend fun getAllLikedSongIds(): DataResult<List<Int>> {
        return repository.getAllLikedSongIds()
    }
}