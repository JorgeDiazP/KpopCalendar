package com.jorgediazp.kpopcalendar.home.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import kotlinx.coroutines.flow.Flow

interface LikedSongsDataSource {

    suspend fun insertLikedSong(song: SongDomainModel): DataResult<Nothing>

    suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>>

    suspend fun deleteLikedSong(songId: Int): DataResult<Nothing>
}