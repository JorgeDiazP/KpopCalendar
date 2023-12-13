package com.jorgediazp.kpopcalendar.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcalendar.home.common.domain.GetSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSongsUseCase: GetSongsUseCase) :
    ViewModel() {

    fun loadData() {
        Log.e("SEA", "Inicio")
        viewModelScope.launch {
            val result = getSongsUseCase.getSongListByQuery("stray")
            Log.e("SEA", result.data.toString())
        }
    }
}