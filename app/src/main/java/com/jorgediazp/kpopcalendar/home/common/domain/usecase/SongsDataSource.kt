package com.jorgediazp.kpopcalendar.home.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel

interface SongsDataSource {

    suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>>

    suspend fun getSongListByQuery(query: String): DataResult<List<SongDomainModel>>
}