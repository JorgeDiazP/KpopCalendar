package com.jorgediazp.kpopcalendar.home.common.data.liked.local

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.data.liked.LikedSongsDataModelExtensions.Companion.toDomainModel
import com.jorgediazp.kpopcalendar.home.common.data.liked.LikedSongsDataModelExtensions.Companion.toLikedDataLocalModel
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.LikedSongsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikedSongsLocalDataSource @Inject constructor(
    private val database: LikedSongsDataBase
) : LikedSongsDataSource {

    override suspend fun insertLikedSong(song: SongDomainModel): DataResult<Nothing> {
        return try {
            database.songsDAO().insertLikedSong(song.toLikedDataLocalModel())
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
                dataList.forEach { dataSong ->
                    try {
                        domainList.add(dataSong.toDomainModel())
                    } catch (e: Exception) {
                        FirebaseCrashlytics.getInstance().recordException(e)
                    }
                }
                domainList
            }
    }

    override suspend fun deleteLikedSong(songId: Long): DataResult<Nothing> {
        return try {
            database.songsDAO().deleteLikedSong(songId)
            DataResult.Success()
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }
}