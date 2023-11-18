package com.jorgediazp.kpopcomebacks.main.domain

import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity
import java.util.Date

interface ComebacksDataSource {

    suspend fun getComebackMap(dateList: List<Date>): DataResult<Map<Date, List<ComebackEntity>>>

    suspend fun getComebackList(dateList: List<String>): DataResult<List<ComebackEntity>>
}