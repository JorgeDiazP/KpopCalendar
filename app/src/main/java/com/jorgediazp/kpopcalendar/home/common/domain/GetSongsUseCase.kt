package com.jorgediazp.kpopcalendar.home.common.domain

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.DateUtils
import com.jorgediazp.kpopcalendar.home.common.di.SongsRepositoryModule
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(
    @SongsRepositoryModule.SongsRepositoryQualifier private val repository: SongsDataSource
) {

    suspend fun getComebackMapByMonth(
        year: Int,
        month: Int
    ): DataResult<Map<String, List<SongDomainModel>>> {
        val dateList = mutableListOf<String>()
        val startDate = LocalDate.of(year, month, 1)
        val endDate = startDate.withDayOfMonth(startDate.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern(DateUtils.SONG_DATE_FORMAT)

        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            dateList.add(currentDate.format(formatter))
            currentDate = currentDate.plusDays(1)
        }

        return repository.getSongMap(dateList)
    }

    suspend fun getSongListByQuery(query: String): DataResult<List<SongDomainModel>> {
        return repository.getSongListByQuery(query)
    }
}