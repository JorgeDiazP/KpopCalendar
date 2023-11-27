package com.jorgediazp.kpopcomebacks.main.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.common.presentation.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.loadData()

        setContent {
            MainScreen()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun CalendarTest(songMap: Map<String, List<ComebackVO>>) {
        Screen {
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
    }
}