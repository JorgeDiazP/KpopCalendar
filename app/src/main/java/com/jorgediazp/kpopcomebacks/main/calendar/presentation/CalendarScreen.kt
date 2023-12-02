package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcomebacks.common.ui.LoadingView
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val backgroundState = viewModel.backgroundState.collectAsStateWithLifecycle()
    val foregroundState = viewModel.foregroundState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.loadData()
    }

    Screen {
        when (backgroundState.value) {
            is CalendarScreenBackgroundState.ShowNothing -> {
                // nothing to do
            }

            is CalendarScreenBackgroundState.ShowSongList -> {
                Column {
                    CalendarTopAppBar(
                        title = "Noviembre",
                        onShowCalendarClick = { viewModel.loadDatePicker() }
                    )
                    CalendarTest(songMap = (backgroundState.value as CalendarScreenBackgroundState.ShowSongList).comebackMap)
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
                        onAccept = { selectedDateMillis -> viewModel.loadSongList(selectedDateMillis) },
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
fun CalendarTest(songMap: Map<String, List<ComebackVO>>) {
    LazyColumn {
        var currentDate = songMap.entries.iterator().next().key
        songMap.forEach { entry ->
            if (currentDate != entry.key) {
                currentDate = entry.key
                stickyHeader {
                    DayCard(text = currentDate)
                }
            }
            itemsIndexed(entry.value) { index, comebackVO ->
                SongCard(
                    isOdd = index % 2 != 0,
                    text = comebackVO.artist,
                    youtubeURL = comebackVO.youtubeUrl,
                    thumbnailUrl = comebackVO.thumbnailUrl
                )
            }
        }
    }
}