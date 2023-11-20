package com.jorgediazp.kpopcomebacks.main.domain

import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity
import java.util.Date

interface ComebacksDataSource {

    suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<ComebackEntity>>>
}