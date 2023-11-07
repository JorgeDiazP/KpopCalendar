package com.jorgediazp.kpopcomebacks.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcomebacks.common.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTest()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarTest() {
    val dayList = mutableListOf<Int>()
    for (i in 1..30) {
        dayList.add(i)
    }
    val songList = mutableListOf<String>()
    for (i in 0..20) {
        songList.add("StrayKids $i")
    }

    Screen {
        Column {
            TitleCard(text = "November") {
                LazyColumn {
                    itemsIndexed(dayList) { index, day ->

                        TitleCard(text = "$day November 2023") {
                            LazyColumn {
                                itemsIndexed(songList) { index, song ->
                                    SongCard(text = song, index % 2 != 0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}