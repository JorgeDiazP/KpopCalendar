package com.jorgediazp.kpopcomebacks.main.common.data

import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.common.di.ComebacksRepositoryModule
import com.jorgediazp.kpopcomebacks.main.common.domain.ComebacksDataSource
import com.jorgediazp.kpopcomebacks.main.common.domain.ComebackEntity
import javax.inject.Inject

class ComebacksRepository @Inject constructor(
    @ComebacksRepositoryModule.ComebacksDataSourceQualifier private val dataSource: ComebacksDataSource
) : ComebacksDataSource {

    override suspend fun getComebackMap(dateList: List<String>): DataResult<Map<String, List<ComebackEntity>>> {
        return dataSource.getComebackMap(dateList)
    }
}