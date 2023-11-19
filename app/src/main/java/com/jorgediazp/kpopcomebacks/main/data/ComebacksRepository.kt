package com.jorgediazp.kpopcomebacks.main.data

import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.di.ComebacksRepositoryModule
import com.jorgediazp.kpopcomebacks.main.domain.ComebacksDataSource
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity
import javax.inject.Inject

class ComebacksRepository @Inject constructor(
    @ComebacksRepositoryModule.ComebacksDataSourceQualifier private val dataSource: ComebacksDataSource
) : ComebacksDataSource {

    override suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<ComebackEntity>>> {
        return dataSource.getComebackMap(dateList)
    }
}