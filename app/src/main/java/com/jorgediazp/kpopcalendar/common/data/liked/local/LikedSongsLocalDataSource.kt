package com.jorgediazp.kpopcalendar.common.data.liked.local

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.data.liked.LikedSongsDataModelExtensions.Companion.toDomainModel
import com.jorgediazp.kpopcalendar.common.data.liked.LikedSongsDataModelExtensions.Companion.toLikedDataLocalModel
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.common.domain.usecase.LikedSongsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikedSongsLocalDataSource @Inject constructor(
    private val database: LikedSongsDataBase
) : LikedSongsDataSource {

    override suspend fun insertLikedSong(
        song: SongDomainModel,
        dateMillis: Long
    ): DataResult<Nothing> {
        return try {
            database.songsDAO().insertLikedSong(song.toLikedDataLocalModel(dateMillis))
            DataResult.Success()

        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }

    override suspend fun getAllLikedSongs(): Flow<List<SongDomainModel>> {
        return database.songsDAO().getAllLikedSongs()
            .map { dataList ->
                val domainList = mutableListOf<SongDomainModel>()
                dataList.sortedByDescending { it.dateMillis }.forEach { dataSong ->
                    try {
                        domainList.add(dataSong.toDomainModel())
                    } catch (e: Exception) {
                        Firebase.crashlytics.recordException(e)
                    }
                }
                domainList
            }
    }

    override suspend fun getAllLikedSongIdsFlow(): Flow<List<Int>> {
        return database.songsDAO().getAllLikedSongIdsFlow()
    }

    override suspend fun getAllLikedSongIds(): DataResult<List<Int>> {
        return try {
            val idList = database.songsDAO().getAllLikedSongIds()
            DataResult.Success(idList)
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }

    override suspend fun deleteLikedSong(songId: Int): DataResult<Nothing> {
        return try {
            database.songsDAO().deleteLikedSong(songId)
            DataResult.Success()
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }
}