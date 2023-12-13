package com.jorgediazp.kpopcalendar.home.common.domain

import com.jorgediazp.kpopcalendar.common.util.DataResult

interface SongsDataSource {

    suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>>

    suspend fun getSongListByQuery(query: String): DataResult<List<SongDomainModel>>
}