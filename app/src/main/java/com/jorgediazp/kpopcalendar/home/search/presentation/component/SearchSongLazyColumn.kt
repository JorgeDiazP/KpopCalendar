package com.jorgediazp.kpopcalendar.home.search.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.ui.component.SongCard

@Composable
fun SearchSongLazyColumn(songList: List<SongPresentationModel>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        items(songList) { song ->
            SongCard(
                isOddRow = song.isOddRow,
                text = song.text,
                youtubeURL = song.youtubeUrl ?: "",
                thumbnailUrl = song.thumbnailUrl ?: ""
            )
        }
    }
}