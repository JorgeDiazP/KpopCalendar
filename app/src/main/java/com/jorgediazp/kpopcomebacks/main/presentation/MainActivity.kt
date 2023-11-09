package com.jorgediazp.kpopcomebacks.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.jorgediazp.kpopcomebacks.common.Screen
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
                            TitleCard(text = currentDate.toString())
                        }
                    }
                    itemsIndexed(entry.value) { index, song ->
                        SongCard(text = song, index % 2 != 0)
                    }
                }
            }
        }
    }

    private fun getSongMap(): Map<Date, List<String>> {
        val songList = mutableListOf<String>()
        for (i in 0..20) {
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