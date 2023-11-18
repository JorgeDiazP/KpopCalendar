package com.jorgediazp.kpopcomebacks.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.presentation.ui.components.DayCard
import com.jorgediazp.kpopcomebacks.main.presentation.ui.components.SongCard
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTest(getSongMap())
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun CalendarTest(songMap: Map<Date, List<String>>) {
        Screen {
            LazyColumn {
                var currentDate = Date()
                songMap.forEach { entry ->
                    if (currentDate != entry.key) {
                        currentDate = entry.key
                        stickyHeader {
                            DayCard(text = currentDate.toString())
                        }
                    }
                    itemsIndexed(entry.value) { index, song ->
                        SongCard(isOdd = index % 2 != 0, text = song, youtubeURL = "https://www.youtube.com/watch?v=OvioeS1ZZ7o", thumbnailUrl = "https://img.youtube.com/vi/OvioeS1ZZ7o/0.jpg")
                    }
                }
            }
        }
    }

    private fun getSongMap(): Map<Date, List<String>> {
        val songList = mutableListOf<String>()
        for (i in 0..5) {
            songList.add("StrayKids $i")
        }
        val map = linkedMapOf<Date, List<String>>()
        for (i in 1..30) {
            val date = Date.from(LocalDate.of(2023, 11, i).atStartOfDay().toInstant(ZoneOffset.UTC))
            map.put(date, songList)
        }
        return map
    }
}