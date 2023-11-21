package com.jorgediazp.kpopcomebacks.main.common.domain

import com.jorgediazp.kpopcomebacks.common.util.DataResult

interface ComebacksDataSource {

    suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<ComebackEntity>>>
}