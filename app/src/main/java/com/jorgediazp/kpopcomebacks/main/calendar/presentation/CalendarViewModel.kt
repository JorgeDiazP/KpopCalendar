package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO
import com.jorgediazp.kpopcomebacks.main.common.domain.ComebackEntity
import com.jorgediazp.kpopcomebacks.main.common.domain.GetComebackUseCase
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
        loadSongList(System.currentTimeMillis())
    }

    fun loadDatePicker() {
        val minTimestamp = 1577836800000
        val maxTimestamp = 1735689599000
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
                        comebackMap = getComebackMap(comebackResult.data)
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
        val pattern = if (dateTime.year == LocalDateTime.now().year) "MMMM" else "MMMM yyyy"
        return dateTime.format(DateTimeFormatter.ofPattern(pattern)).replaceFirstChar(Char::uppercase)
    }

    private fun getComebackMap(remoteMap: Map<String, List<ComebackEntity>>): Map<String, List<ComebackVO>> {
        val comebackMap = mutableMapOf<String, List<ComebackVO>>()
        remoteMap.forEach { (dateString, comebackEntityList) ->
            val comebackList = mutableListOf<ComebackVO>()
            comebackEntityList.forEach { comebackEntity ->
                comebackList.add(
                    ComebackVO(
                        artist = comebackEntity.artist + " - " + comebackEntity.titleTrack,
                        youtubeUrl = getYoutubeUrl(comebackEntity.musicVideo ?: "=a"),
                        thumbnailUrl = getThumbnailUrl(comebackEntity.musicVideo ?: "=a")
                    )
                )
            }
            comebackMap.put(dateString, comebackList)
        }
        return comebackMap
    }

    private fun getYoutubeUrl(youtubeUrl: String): String {
        val id = youtubeUrl.substringAfter(".be/").substringBefore("?")
        return "https://www.youtube.com/watch?v=${id}"
    }

    private fun getThumbnailUrl(youtubeUrl: String): String {
        val id = youtubeUrl.substringAfter(".be/").substringBefore("?")
        return "https://img.youtube.com/vi/${id}/0.jpg"
    }
}