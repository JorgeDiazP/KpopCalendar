package com.jorgediazp.kpopcalendar.home.common.data.liked

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.LikedSongsRepositoryModule
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.LikedSongsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikedSongsRepository @Inject constructor(
    @LikedSongsRepositoryModule.LikedSongsDataSourceQualifier private val dataSource: LikedSongsDataSource
) : LikedSongsDataSource {

    override suspend fun insertLikedSong(song: SongDomainModel): DataResult<Nothing> {
        return dataSource.insertLikedSong(song)
    }

    override suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>> {
        return dataSource.getAllLikedSongs()
    }

    override suspend fun deleteLikedSong(songId: Int): DataResult<Nothing> {
        return dataSource.deleteLikedSong(songId)
    }
}