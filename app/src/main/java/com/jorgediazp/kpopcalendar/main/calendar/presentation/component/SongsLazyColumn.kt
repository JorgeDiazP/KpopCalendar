package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.DatePresentationModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongsLazyColumn(dateList: List<DatePresentationModel>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        var currentDate = ""
        dateList.forEach { dateModel ->
            if (currentDate != dateModel.date) {
                stickyHeader {
                    DateCard(text = dateModel.date, isToday = dateModel.isToday)
                }
                currentDate = dateModel.date
            }
            if (dateModel.songList.isNotEmpty()) {
                itemsIndexed(dateModel.songList) { index, song ->
                    SongCard(
                        isOdd = song.isOddRow,
                        text = song.text,
                        youtubeURL = song.youtubeUrl ?: "",
                        thumbnailUrl = song.thumbnailUrl ?: ""
                    )
                }
            } else {
                item {
                    SongsEmptyCard()
                }
            }
        }
    }
}