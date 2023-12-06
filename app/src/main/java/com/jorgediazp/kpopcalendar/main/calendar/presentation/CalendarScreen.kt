package com.jorgediazp.kpopcalendar.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.ui.LoadingView
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.DateCard
import com.jorgediazp.kpopcalendar.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.DatePresentationModel

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
        when (backgroundState.value) {
            is CalendarScreenBackgroundState.ShowNothing -> {
                // nothing to do
            }

            is CalendarScreenBackgroundState.ShowDateList -> {
                (backgroundState.value as CalendarScreenBackgroundState.ShowDateList).let { state ->
                    Column {
                        CalendarTopAppBar(
                            title = state.topBarTitle,
                            onShowCalendarClick = { viewModel.loadDatePicker() }
                        )
                        CalendarTest(dateList = state.dateList)
                    }
                }
            }

            is CalendarScreenBackgroundState.ShowError -> {
                // TODO
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarTest(dateList: List<DatePresentationModel>) {
    LazyColumn {
        var currentDate = ""
        dateList.forEach { dateModel ->
            if (currentDate != dateModel.date) {
                stickyHeader {
                    DateCard(text = dateModel.date, isToday = dateModel.isToday)
                }
                currentDate = dateModel.date
            }
            itemsIndexed(dateModel.songList) { index, song ->
                SongCard(
                    isOdd = index % 2 != 0,
                    text = song.text,
                    youtubeURL = song.youtubeUrl ?: "",
                    thumbnailUrl = song.thumbnailUrl ?: ""
                )
            }
        }
    }
}