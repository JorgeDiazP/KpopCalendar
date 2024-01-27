package com.jorgediazp.kpopcalendar.home.search.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.presentation.model.SongPresentationModel
import com.jorgediazp.kpopcalendar.common.presentation.ui.component.SongCard

@Composable
fun SearchSongLazyColumn(
    songList: List<SongPresentationModel>,
    onLikeClicked: (song: SongPresentationModel) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
            )
        }
        items(songList) { song ->
            SongCard(
                isOddRow = song.isOddRow,
                text = song.text,
                youtubeURL = song.youtubeUrl ?: "",
                thumbnailUrl = song.thumbnailUrl ?: "",
                liked = song.liked,
                onLikeClicked = { onLikeClicked(song) }
            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )
        }
    }
}