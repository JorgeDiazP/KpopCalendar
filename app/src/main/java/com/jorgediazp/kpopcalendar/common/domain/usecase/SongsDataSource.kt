package com.jorgediazp.kpopcalendar.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel

interface SongsDataSource {

    suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>>

    suspend fun getSongMapByQuery(query: String): DataResult<Map<Int, SongDomainModel>>
}