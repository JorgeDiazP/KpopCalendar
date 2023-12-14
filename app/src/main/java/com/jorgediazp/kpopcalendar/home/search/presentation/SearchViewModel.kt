package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcalendar.common.util.DataResult
import com.jorgediazp.kpopcalendar.home.common.domain.GetSongsUseCase
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSongsUseCase: GetSongsUseCase) :
    ViewModel() {

    val state = MutableStateFlow<SearchScreenState>(SearchScreenState.ShowNothing)

    fun loadSongListByQuery(query: String) {
        if (query.length > 1) {
            viewModelScope.launch {
                val result = getSongsUseCase.getSongListByQuery(query)
                if (result is DataResult.Success && result.data != null) {
                    if (result.data.isNotEmpty()) {
                        state.value = SearchScreenState.ShowNothing

                    } else {
                        state.value = SearchScreenState.ShowNoResults
                    }
                } else {
                    state.value = SearchScreenState.ShowError
                }
            }
        } else {
            state.value = SearchScreenState.ShowNothing
        }
    }
}