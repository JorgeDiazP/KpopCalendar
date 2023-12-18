package com.jorgediazp.kpopcalendar.home.common.data.songs

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.di.SongsRepositoryModule
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.SongsDataSource
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
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