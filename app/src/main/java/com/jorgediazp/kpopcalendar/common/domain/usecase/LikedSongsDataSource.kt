package com.jorgediazp.kpopcalendar.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import kotlinx.coroutines.flow.Flow

interface LikedSongsDataSource {

    suspend fun insertLikedSong(song: SongDomainModel, dateMillis: Long): DataResult<Nothing>

    suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>>

    suspend fun getAllLikedSongIdsFlow(): Flow<List<Int>>

    suspend fun getAllLikedSongIds(): DataResult<List<Int>>

    suspend fun deleteLikedSong(songId: Int): DataResult<Nothing>
}