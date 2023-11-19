package com.jorgediazp.kpopcomebacks.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.main.domain.GetComebackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getComebackUseCase: GetComebackUseCase
) : ViewModel() {

    fun loadData() {
        viewModelScope.launch {
            val comebackResult = getComebackUseCase.getComebackMapByMonth(2023, 11)
            if (comebackResult is DataResult.Success) {
                Log.e("KPC", "Success")
            } else {
                Log.e("KPC", "Error")
            }
        }
    }
}