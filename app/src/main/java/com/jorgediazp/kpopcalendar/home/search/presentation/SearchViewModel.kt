package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.domain.usecase.GetSongsUseCase
import com.jorgediazp.kpopcalendar.home.common.domain.model.SongDomainModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.PresentationModelExtensions.Companion.toPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationType
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSongsUseCase: GetSongsUseCase) :
    ViewModel() {

    val state = MutableStateFlow<SearchScreenState>(SearchScreenState.ShowNothing)
    private var currentQueryId = 0

    fun loadSongListByQuery(query: String) {
        currentQueryId++
        val queryId = currentQueryId

        if (query.length > 1) {
            viewModelScope.launch {
                val result = getSongsUseCase.getSongListByQuery(query)
                if (queryId >= currentQueryId) {
                    // Queries finished late are not shown
                    if (result is DataResult.Success && result.data != null) {
                        if (result.data.isNotEmpty()) {
                            state.value =
                                SearchScreenState.ShowSongList(getSongPresentationList(result.data))

                        } else {
                            state.value = SearchScreenState.ShowNoResults
                        }
                    } else {
                        state.value = SearchScreenState.ShowError
                    }
                }
            }
        } else {
            state.value = SearchScreenState.ShowNothing
        }
    }

    private fun getSongPresentationList(domainList: List<SongDomainModel>): List<SongPresentationModel> {
        val presentationList = mutableListOf<SongPresentationModel>()
        var isOddRow = true
        domainList.forEach { songDomain ->
            try {
                val presentationSong = songDomain.toPresentationModel(isOddRow, false)
                if (songDomain.ost == null && presentationSong.type == SongPresentationType.RELEASED) {
                    // Do not add songs from ost or unreleased
                    presentationList.add(presentationSong)
                    isOddRow = !isOddRow
                }
            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
            }
        }
        return presentationList
    }
}