package com.jorgediazp.kpopcomebacks.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgediazp.kpopcomebacks.common.util.DataResult
import com.jorgediazp.kpopcomebacks.common.util.DateUtils
import com.jorgediazp.kpopcomebacks.main.data.remote.ComebacksRemoteDataSource
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.GregorianCalendar

class MainViewModel : ViewModel() {

    fun loadData() {
        val calendar = GregorianCalendar()
        calendar.set(2023, 11 - 1, 1)
        val daysInMonth = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat(DateUtils.COMEBACK_DATE_FORMAT)
        val dateList = mutableListOf<String>()
        for (i in 1..daysInMonth) {
            dateList.add(sdf.format(calendar.time))
            calendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
        }
        viewModelScope.launch {
            val comebackResult = ComebacksRemoteDataSource().getComebackMap(dateList)
            if (comebackResult is DataResult.Success) {
                Log.e("KPC", "Success")
            } else {
                Log.e("KPC", "Error")
            }
        }
    }
}