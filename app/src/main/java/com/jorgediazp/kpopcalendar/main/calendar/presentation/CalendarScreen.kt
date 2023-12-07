package com.jorgediazp.kpopcalendar.main.calendar.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.ui.LoadingView
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.SongsLazyColumn
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenForegroundState

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val backgroundState = viewModel.backgroundState.collectAsStateWithLifecycle()
    val foregroundState = viewModel.foregroundState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

    Screen {
        Column {
            var topBarTitle by remember { mutableStateOf("")}
            CalendarTopAppBar(
                title = topBarTitle,
                onShowCalendarClick = { viewModel.loadDatePicker() }
            )
            when (backgroundState.value) {
                is CalendarScreenBackgroundState.ShowNothing -> {
                    // nothing to do
                }

                is CalendarScreenBackgroundState.ShowDateList -> {
                    (backgroundState.value as CalendarScreenBackgroundState.ShowDateList).let { state ->
                        topBarTitle = state.topBarTitle
                        SongsLazyColumn(dateList = state.dateList)
                    }
                }

                is CalendarScreenBackgroundState.ShowError -> {
                    (backgroundState.value as CalendarScreenBackgroundState.ShowError).let { state ->
                        topBarTitle = state.topBarTitle

                    }
                }
            }
        }
        when (foregroundState.value) {
            is CalendarScreenForegroundState.ShowNothing -> {
                // nothing to do
            }

            is CalendarScreenForegroundState.ShowLoading -> {
                LoadingView()
            }

            is CalendarScreenForegroundState.ShowCalendarPicker -> {
                (foregroundState.value as CalendarScreenForegroundState.ShowCalendarPicker).let { state ->
                    CalendarPicker(
                        minTimestamp = state.minTimestamp,
                        maxTimestamp = state.maxTimestamp,
                        onAccept = { selectedDateMillis -> viewModel.loadDateList(selectedDateMillis) },
                        onCancel = {
                            viewModel.foregroundState.value =
                                CalendarScreenForegroundState.ShowNothing
                        })
                }
            }
        }
    }
}