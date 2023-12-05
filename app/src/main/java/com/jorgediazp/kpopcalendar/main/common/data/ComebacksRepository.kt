package com.jorgediazp.kpopcalendar.main.common.data

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.main.common.di.ComebacksRepositoryModule
import com.jorgediazp.kpopcalendar.main.common.domain.ComebacksDataSource
import com.jorgediazp.kpopcalendar.main.common.domain.SongDomainModel
import javax.inject.Inject

class ComebacksRepository @Inject constructor(
    @ComebacksRepositoryModule.ComebacksDataSourceQualifier private val dataSource: ComebacksDataSource
) : ComebacksDataSource {

    override suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<SongDomainModel>>> {
        return dataSource.getComebackMap(dateList)
    }
}