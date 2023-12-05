package com.jorgediazp.kpopcalendar.main.common.domain

import com.jorgediazp.kpopcalendar.common.util.DataResult

interface ComebacksDataSource {

    suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>>
}