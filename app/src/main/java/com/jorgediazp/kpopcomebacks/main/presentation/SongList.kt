package com.jorgediazp.kpopcomebacks.main.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable

@Composable
fun SongList(text: String, list: List<String>) {
    TitleCard(text = "text") {
        LazyColumn {
            itemsIndexed(list) { index, song ->
                SongCard(text = song, index % 2 != 0)
            }
        }
    }
}