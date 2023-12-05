package com.jorgediazp.kpopcalendar.main.calendar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_AND_YEAR_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.DateUtils.Companion.MONTH_DATE_FORMAT
import com.jorgediazp.kpopcalendar.common.util.FirebaseRemoteConfigKey
import com.jorgediazp.kpopcalendar.common.util.FirebaseUtils.Companion.getRemoteConfigLong
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.main.common.domain.SongDomainModel
import com.jorgediazp.kpopcalendar.main.common.domain.GetComebackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getComebackUseCase: GetComebackUseCase
) : ViewModel() {

    var dataLoaded = false
    val backgroundState =
        MutableStateFlow<CalendarScreenBackgroundState>(CalendarScreenBackgroundState.ShowNothing)
    val foregroundState =
        MutableStateFlow<CalendarScreenForegroundState>(CalendarScreenForegroundState.ShowNothing)

    fun loadData() {
        dataLoaded = true
        loadSongList(Instant.now().toEpochMilli())
    }

    fun loadDatePicker() {
        val minTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MIN_TIME)
        val maxTimestamp = getRemoteConfigLong(FirebaseRemoteConfigKey.CALENDAR_MAX_TIME)
        foregroundState.value =
            CalendarScreenForegroundState.ShowCalendarPicker(minTimestamp, maxTimestamp)
    }

    fun loadSongList(selectedDateMillis: Long?) {
        selectedDateMillis?.let {
            val dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(selectedDateMillis),
                ZoneId.systemDefault()
            )
            foregroundState.value = CalendarScreenForegroundState.ShowLoading

            viewModelScope.launch {
                val comebackResult =
                    getComebackUseCase.getComebackMapByMonth(dateTime.year, dateTime.monthValue)
                if (comebackResult is DataResult.Success && comebackResult.data != null) {
                    backgroundState.value = CalendarScreenBackgroundState.ShowSongList(
                        topBarTitle = getMonthTitle(dateTime),
                        comebackMap = getPresentationSongMap(comebackResult.data)
                    )
                } else {
                    Log.e("KPC", "Error")
                }
                foregroundState.value = CalendarScreenForegroundState.ShowNothing
            }

        } ?: {
            foregroundState.value = CalendarScreenForegroundState.ShowNothing
        }
    }

    private fun getMonthTitle(dateTime: LocalDateTime): String {
        val pattern =
            if (dateTime.year == LocalDateTime.now().year) MONTH_DATE_FORMAT else MONTH_AND_YEAR_DATE_FORMAT
        return dateTime.format(DateTimeFormatter.ofPattern(pattern))
            .replaceFirstChar(Char::uppercase)
    }

    private fun getPresentationSongMap(domainMap: Map<String, List<SongDomainModel>>): Map<String, List<SongPresentationModel>> {
        val presentationMap = mutableMapOf<String, List<SongPresentationModel>>()
        domainMap.forEach { (dateString, comebackEntityList) ->
            val comebackList = mutableListOf<SongPresentationModel>()
            comebackEntityList.forEach { songDomain ->
                try {
                    comebackList.add(songDomain.toPresentationModel())
                } catch (e: Exception) {
                    Firebase.crashlytics.recordException(e)
                }
            }
            presentationMap[dateString] = comebackList
        }
        return presentationMap
    }
}