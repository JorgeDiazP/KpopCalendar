package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val songMap = viewModel.comebackMap.observeAsState()
    var refreshCount by remember { mutableIntStateOf(1) }

    LaunchedEffect(key1 = refreshCount) {
        viewModel.loadData()
    }

    Screen {
        songMap.value?.let { songMap ->
            Column {
                CalendarTopAppBar(title = "Noviembre")
                CalendarTest(songMap = songMap)
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