package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO

@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    val songMap = viewModel.comebackMap.observeAsState()
    var refreshCount by remember { mutableIntStateOf(1) }

    // API call
    LaunchedEffect(key1 = refreshCount) {
        viewModel.loadData()
    }

    Screen {
        songMap.value?.let { songMap ->
            CalendarTest(songMap = songMap)
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