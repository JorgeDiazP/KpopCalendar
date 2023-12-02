package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgediazp.kpopcomebacks.common.ui.LoadingView
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.CalendarState
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val state = viewModel.state.observeAsState()
    val showLoading = viewModel.showLoading.observeAsState()
    val showCalendarPicker = viewModel.showCalendarPicker.observeAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.loadData()
    }

    Screen {
        when (state.value) {
            is CalendarState.ShowSongList -> {
                Column {
                    CalendarTopAppBar(
                        title = "Noviembre",
                        onShowCalendarClick = { viewModel.showCalendarPicker.value = true })
                    CalendarTest(songMap = (state.value as CalendarState.ShowSongList).comebackMap)
                }
            }

            else -> {
                // nothing to do
            }
        }
        if (showLoading.value == true) {
            LoadingView()
        }
        if (showCalendarPicker.value == true) {
            CalendarPicker(onCancel = {}, onAccept = {})
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