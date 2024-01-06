package com.jorgediazp.kpopcalendar.home.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.LikedSongsRepositoryModule
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import java.time.Instant
import javax.inject.Inject

class InsertLikedSongsUseCase @Inject constructor(
    @LikedSongsRepositoryModule.LikedSongsRepositoryQualifier private val repository: LikedSongsDataSource
) {

    suspend fun insertLikedSong(song: SongDomainModel): DataResult<Nothing> {
        return repository.insertLikedSong(song, Instant.now().toEpochMilli())
    }
}