package com.jorgediazp.kpopcomebacks.main.common.data

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.common.data.DataModelExtensions.Companion.COMEBACKS_COLLECTION
import com.jorgediazp.kpopcomebacks.main.common.data.DataModelExtensions.Companion.COMEBACKS_FIELD
import com.jorgediazp.kpopcomebacks.main.common.data.DataModelExtensions.Companion.DATE_FIELD
import com.jorgediazp.kpopcomebacks.main.common.data.DataModelExtensions.Companion.toDomainModel
import com.jorgediazp.kpopcomebacks.main.common.domain.ComebacksDataSource
import com.jorgediazp.kpopcomebacks.main.common.domain.SongDomainModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ComebacksRemoteDataSource @Inject constructor() : ComebacksDataSource {

    companion object {
        private const val FIRESTORE_IN_MAX_SIZE = 30
    }

    override suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>> {
        return try {
            val comebackMap: MutableMap<String, List<SongDomainModel>> =
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
                        comebackMap[document.get(DATE_FIELD) as String] =
                            (document.get(COMEBACKS_FIELD) as List<HashMap<String, Any>>).map { it.toDomainModel() }
                    } catch (e: Exception) {
                        Firebase.crashlytics.recordException(e)
                    }
                }
            }
            DataResult.Success(data = comebackMap)

        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            DataResult.Error(message = e.message, exception = e)
        }
    }
}