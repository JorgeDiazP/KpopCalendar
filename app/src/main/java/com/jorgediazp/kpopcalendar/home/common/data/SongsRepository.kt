package com.jorgediazp.kpopcalendar.home.common.data

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.SongsRepositoryModule
import com.jorgediazp.kpopcalendar.home.common.domain.SongsDataSource
import com.jorgediazp.kpopcalendar.home.common.domain.SongDomainModel
import javax.inject.Inject

class SongsRepository @Inject constructor(
    @SongsRepositoryModule.SongsDataSourceQualifier private val dataSource: SongsDataSource
) : SongsDataSource {

    override suspend fun getSongMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>> {
        return dataSource.getSongMap(dateList)
    }

    override suspend fun getSongListByQuery(query: String): DataResult<List<SongDomainModel>> {
        return dataSource.getSongListByQuery(query)
    }
}