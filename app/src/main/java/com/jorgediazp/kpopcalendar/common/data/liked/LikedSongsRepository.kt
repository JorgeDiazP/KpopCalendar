package com.jorgediazp.kpopcalendar.common.data.liked

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.di.LikedSongsRepositoryModule
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.common.domain.usecase.LikedSongsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikedSongsRepository @Inject constructor(
    @LikedSongsRepositoryModule.LikedSongsDataSourceQualifier private val dataSource: LikedSongsDataSource
) : LikedSongsDataSource {

    override suspend fun insertLikedSong(song: SongDomainModel, dateMillis: Long): DataResult<Nothing> {
        return dataSource.insertLikedSong(song, dateMillis)
    }

    override suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>> {
        return dataSource.getAllLikedSongs()
    }

    override suspend fun getAllLikedSongIdsFlow(): Flow<List<Int>> {
        return dataSource.getAllLikedSongIdsFlow()
    }

    override suspend fun getAllLikedSongIds(): DataResult<List<Int>> {
        return dataSource.getAllLikedSongIds()
    }

    override suspend fun deleteLikedSong(songId: Int): DataResult<Nothing> {
        return dataSource.deleteLikedSong(songId)
    }
}