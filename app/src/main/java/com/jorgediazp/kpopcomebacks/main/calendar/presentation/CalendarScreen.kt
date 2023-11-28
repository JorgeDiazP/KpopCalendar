package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO

@OptIn(ExperimentalMaterial3Api::class)
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
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    title = {
                        Card(modifier = Modifier.clickable {  }) {
                            Text(text = "Noviembre", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                )
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