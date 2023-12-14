package com.jorgediazp.kpopcalendar.home.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        viewModelScope.launch {
            val result = getSongsUseCase.getSongListByQuery(query)
        }
    }
}