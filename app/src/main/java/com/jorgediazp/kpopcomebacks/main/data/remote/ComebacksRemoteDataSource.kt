package com.jorgediazp.kpopcomebacks.main.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.common.util.DateUtils
import com.jorgediazp.kpopcomebacks.common.util.DateUtils.Companion.getStringFormat
import com.jorgediazp.kpopcomebacks.main.data.ComebackExtensions.Companion.mapToEntity
import com.jorgediazp.kpopcomebacks.main.data.remote.model.ComebackRemoteDTO
import com.jorgediazp.kpopcomebacks.main.domain.ComebacksDataSource
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity
import kotlinx.coroutines.tasks.await
import java.util.Date

class ComebacksRemoteDataSource : ComebacksDataSource {

    override suspend fun getComebackMap(dateList: List<Date>): DataResult<Map<Date, List<ComebackEntity>>> {
        return try {
            val db = Firebase.firestore
            val result = db.collection("comebacks")
                .whereIn("date", dateList.getStringFormat(DateUtils.COMEBACK_DATE_FORMAT))
                .get()
                .await()

            val comebackMap: Map<Date, List<ComebackEntity>> =
                dateList.associateBy({ date -> date }, { mutableListOf() })
            dateList.forEach { date ->

            }
            DataResult.Error()
        } catch (e: Exception) {
            DataResult.Error(message = e.message, exception = e)
        }
    }

    override suspend fun getComebackList(dateList: List<String>): DataResult<List<ComebackEntity>> {
        return try {
            val db = Firebase.firestore
            val result = db.collection("comebacks")
                .whereIn("date", dateList)
                .get()
                .await()

            val comebackList = mutableListOf<ComebackEntity>()
            result.documents.forEach { document ->
                try {
                    document.toObject(ComebackRemoteDTO::class.java)?.let { remoteDto ->
                        comebackList.add(remoteDto.mapToEntity())
                    }
                } catch (e: Exception) {
                    // nothing to do
                }
            }
            DataResult.Success(data = comebackList)

        } catch (e: Exception) {
            DataResult.Error(message = e.message, exception = e)
        }
    }
}