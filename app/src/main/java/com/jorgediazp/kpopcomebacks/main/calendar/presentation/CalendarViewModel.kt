package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO
import com.jorgediazp.kpopcomebacks.main.common.domain.ComebackEntity
import com.jorgediazp.kpopcomebacks.main.common.domain.GetComebackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getComebackUseCase: GetComebackUseCase
) : ViewModel() {
    val state = MutableLiveData<CalendarState>()
    val showLoading = MutableLiveData<Boolean>()

    fun loadData() {
        showLoading.postValue(true)

        viewModelScope.launch {
            val comebackResult = getComebackUseCase.getComebackMapByMonth(2023, 11)
            if (comebackResult is DataResult.Success && comebackResult.data != null) {
                state.postValue(CalendarState.ShowSongList(getComebackMap(comebackResult.data)))
                showLoading.postValue(false)
            } else {
                Log.e("KPC", "Error")
            }
        }
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