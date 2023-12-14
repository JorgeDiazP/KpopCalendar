package com.jorgediazp.kpopcalendar.home.common.data

import android.content.Context
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.InternetUtils
import com.jorgediazp.kpopcalendar.home.common.data.DataModelExtensions.Companion.DATE_FIELD
import com.jorgediazp.kpopcalendar.home.common.data.DataModelExtensions.Companion.SONGS_COLLECTION
import com.jorgediazp.kpopcalendar.home.common.data.DataModelExtensions.Companion.SONGS_FIELD
import com.jorgediazp.kpopcalendar.home.common.data.DataModelExtensions.Companion.TAGS_FIELD
import com.jorgediazp.kpopcalendar.home.common.data.DataModelExtensions.Companion.toDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.domain.SongsDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsRemoteDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    SongsDataSource {

    companion object {
        private const val FIRESTORE_IN_MAX_SIZE = 30
    }

    override suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>> {
        return withContext(Dispatchers.IO) {
            try {
                if (InternetUtils.isInternetAvailable(context)) {
                    val songMap: MutableMap<String, List<SongDomainModel>> =
                        dateList.associateBy({ date -> date }, { mutableListOf<SongDomainModel>() })
                            .toMutableMap()

                    val db = Firebase.firestore

                    dateList.chunked(FIRESTORE_IN_MAX_SIZE).forEach { dateListChunk ->
                        val result = db.collection(SONGS_COLLECTION)
                            .whereIn(DATE_FIELD, dateListChunk)
                            .get()
                            .await()

                        result.documents.forEach { document ->
                            try {
                                songMap[document.get(DATE_FIELD) as String] =
                                    (document.get(SONGS_FIELD) as List<HashMap<String, Any>>).map { it.toDomainModel() }
                            } catch (e: Exception) {
                                Firebase.crashlytics.recordException(e)
                            }
                        }
                    }
                    DataResult.Success(data = songMap)

                } else {
                    DataResult.Error(message = "Internet is not available")
                }

            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
                DataResult.Error(message = e.message, exception = e)
            }
        }
    }

    override suspend fun getSongListByQuery(query: String): DataResult<List<SongDomainModel>> {
        return withContext(Dispatchers.IO) {
            try {
                if (InternetUtils.isInternetAvailable(context)) {
                    val songList = mutableListOf<SongDomainModel>()
                    val db = Firebase.firestore
                    val result = db.collection(SONGS_COLLECTION)
                        .whereArrayContains(TAGS_FIELD, query)
                        .get()
                        .await()

                    result.documents.forEach { document ->
                        try {
                            (document.get(SONGS_FIELD) as List<HashMap<String, Any>>).map { it.toDomainModel() }
                                .forEach { song ->
                                    if (songMatchesQuery(query, song)) {
                                        songList.add(song)
                                    }
                                }

                        } catch (e: Exception) {
                            Firebase.crashlytics.recordException(e)
                        }
                    }
                    DataResult.Success(songList)

                } else {
                    DataResult.Error(message = "Internet is not available")
                }
            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
                DataResult.Error(message = e.message, exception = e)
            }
        }
    }

    private fun songMatchesQuery(query: String, song: SongDomainModel): Boolean {
        return song.titleTrack?.lowercase()?.contains(query) == true
                || song.artist?.lowercase()?.contains(query) == true
                || song.artists?.any { it.lowercase().contains(query) } == true
    }
}