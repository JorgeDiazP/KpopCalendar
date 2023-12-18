package com.jorgediazp.kpopcalendar.home.common.domain.usecase

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
}