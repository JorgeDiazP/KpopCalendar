package com.jorgediazp.kpopcomebacks.main.domain

import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.common.util.DateUtils
import com.jorgediazp.kpopcomebacks.main.di.ComebacksRepositoryModule
import com.jorgediazp.kpopcomebacks.main.domain.entity.ComebackEntity
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import javax.inject.Inject

class GetComebackUseCase @Inject constructor(
    @ComebacksRepositoryModule.ComebacksRepositoryQualifier private val repository: ComebacksDataSource
) {

    suspend fun getComebackMapByMonth(
        year: Int,
        month: Int
    ): DataResult<Map<String, List<ComebackEntity>>> {
        val calendar = GregorianCalendar()
        calendar.set(year, month - 1, 1)
        val daysInMonth = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat(DateUtils.COMEBACK_DATE_FORMAT)
        val dateList = mutableListOf<String>()
        for (i in 1..daysInMonth) {
            dateList.add(sdf.format(calendar.time))
            calendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
        }

        return repository.getComebackMap(dateList)
    }
}