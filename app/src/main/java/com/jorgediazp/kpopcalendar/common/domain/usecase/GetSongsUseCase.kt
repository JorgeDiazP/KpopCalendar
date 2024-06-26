package com.jorgediazp.kpopcalendar.common.domain.usecase

import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.DateUtils
import com.jorgediazp.kpopcalendar.common.di.SongsRepositoryModule
import com.jorgediazp.kpopcalendar.common.domain.model.SongDomainModel
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

    suspend fun getSongMapByQuery(query: String): DataResult<Map<Int, SongDomainModel>> {
        return repository.getSongMapByQuery(query)
    }
}