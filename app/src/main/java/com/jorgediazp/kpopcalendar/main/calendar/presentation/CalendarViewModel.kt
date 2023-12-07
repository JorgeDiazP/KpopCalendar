package com.jorgediazp.kpopcalendar.main.calendar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_AND_YEAR_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.SONG_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.FirebaseRemoteConfigKey
import com.jorgediazp.kpopcalendar.common.util.FirebaseUtils.Companion.getRemoteConfigLong
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.DatePresentationModel
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.main.common.domain.GetSongsUseCase
import com.jorgediazp.kpopcalendar.main.common.domain.SongDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase
) : ViewModel() {

    var dataLoaded = false
    val backgroundState =
        MutableStateFlow<CalendarScreenBackgroundState>(CalendarScreenBackgroundState.ShowNothing)
    val foregroundState =
        MutableStateFlow<CalendarScreenForegroundState>(CalendarScreenForegroundState.ShowNothing)

    fun loadData() {
        dataLoaded = true
        loadDateList(Instant.now().toEpochMilli())
    }

    fun loadDatePicker() {
        val minTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MIN_TIME)
        val maxTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MAX_TIME)
        foregroundState.value =
            CalendarScreenForegroundState.ShowCalendarPicker(minTimestamp, maxTimestamp)
    }

    fun loadDateList(selectedDateMillis: Long?) {
        selectedDateMillis?.let {
            loadDateList(selectedDateMillis)
        } ?: {
            foregroundState.value = CalendarScreenForegroundState.ShowNothing
        }
    }

    private fun loadDateList(selectedDateMillis: Long) {
        val dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(selectedDateMillis),
            ZoneId.systemDefault()
        )
        foregroundState.value = CalendarScreenForegroundState.ShowLoading

        viewModelScope.launch {
            val result =
                getSongsUseCase.getComebackMapByMonth(dateTime.year, dateTime.monthValue)
            if (result is DataResult.Success && result.data != null) {
                backgroundState.value = CalendarScreenBackgroundState.ShowDateList(
                    topBarTitle = getTopBarTitle(dateTime),
                    dateList = getDatePresentationList(result.data)
                )
            } else {
                backgroundState.value =
                    CalendarScreenBackgroundState.ShowError(topBarTitle = getTopBarTitle(dateTime))
            }
            foregroundState.value = CalendarScreenForegroundState.ShowNothing
        }
    }

    private fun getTopBarTitle(dateTime: LocalDateTime): String {
        val pattern =
            if (dateTime.year == LocalDateTime.now().year) MONTH_DATE_FORMAT else MONTH_AND_YEAR_DATE_FORMAT
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
            .replaceFirstChar(Char::uppercase)
    }

    private fun getDatePresentationList(domainMap: Map<String, List<SongDomainModel>>): List<DatePresentationModel> {
        val dateList = mutableListOf<DatePresentationModel>()
        var isOddRow = true
        domainMap.forEach { (dateString, songDomainList) ->
            // The list of songs has random order so user can discover different songs
            val songDomainListAux = songDomainList.shuffled()
            val songPresentationList = mutableListOf<SongPresentationModel>()
            songDomainListAux.forEach { songDomain ->
                try {
                    if (songDomain.ost == null) {
                        // Do not add songs from ost
                        songPresentationList.add(songDomain.toPresentationModel(isOddRow))
                        isOddRow = !isOddRow
                    }
                } catch (e: Exception) {
                    Firebase.crashlytics.recordException(e)
                }
            }
            dateList.add(
                DatePresentationModel(
                    date = getDisplayDate(dateString),
                    isToday = dateStringIsToday(dateString),
                    songPresentationList
                )
            )
        }
        return dateList
    }

    private fun getDisplayDate(dateString: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern(SONG_DATE_FORMAT)
        val date = LocalDate.parse(dateString, dateFormatter)
        return "${
            date.dayOfWeek.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ).replaceFirstChar(Char::uppercase)
        } ${date.dayOfMonth}"
    }

    private fun dateStringIsToday(dateString: String): Boolean {
        val dateFormatter = DateTimeFormatter.ofPattern(SONG_DATE_FORMAT)
        val date = LocalDate.parse(dateString, dateFormatter)
        return date.equals(LocalDate.now(ZoneId.systemDefault()))
    }
}