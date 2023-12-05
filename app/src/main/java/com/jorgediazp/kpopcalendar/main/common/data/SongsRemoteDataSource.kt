package com.jorgediazp.kpopcalendar.main.common.data

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.main.common.data.DataModelExtensions.Companion.COMEBACKS_COLLECTION
import com.jorgediazp.kpopcalendar.main.common.data.DataModelExtensions.Companion.COMEBACKS_FIELD
import com.jorgediazp.kpopcalendar.main.common.data.DataModelExtensions.Companion.DATE_FIELD
import com.jorgediazp.kpopcalendar.main.common.data.DataModelExtensions.Companion.toDomainModel
import com.jorgediazp.kpopcalendar.main.common.domain.SongDomainModel
import com.jorgediazp.kpopcalendar.main.common.domain.SongsDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SongsRemoteDataSource @Inject constructor() : SongsDataSource {

    companion object {
        private const val FIRESTORE_IN_MAX_SIZE = 30
    }

    override suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>> {
        return try {
            val songMap: MutableMap<String, List<SongDomainModel>> =
                dateList.associateBy({ date -> date }, { mutableListOf<SongDomainModel>() })
                    .toMutableMap()

            val db = Firebase.firestore

            dateList.chunked(FIRESTORE_IN_MAX_SIZE).forEach { dateListChunk ->
                val result = db.collection(COMEBACKS_COLLECTION)
                    .whereIn(DATE_FIELD, dateListChunk)
                    .get()
                    .await()

                result.documents.forEach { document ->
                    try {
                        songMap[document.get(DATE_FIELD) as String] =
                            (document.get(COMEBACKS_FIELD) as List<HashMap<String, Any>>).map { it.toDomainModel() }
                    } catch (e: Exception) {
                        Firebase.crashlytics.recordException(e)
                    }
                }
            }
            DataResult.Success(data = songMap)

        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }
}