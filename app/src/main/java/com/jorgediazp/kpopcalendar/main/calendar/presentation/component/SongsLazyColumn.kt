package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.DatePresentationModel
import com.jorgediazp.kpopcalendar.main.calendar.presentation.model.SongPresentationType

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
                items(dateModel.songList) { song ->
                    when (song.type) {
                        SongPresentationType.RELEASED -> {
                            SongCard(
                                isOddRow = song.isOddRow,
                                text = song.text,
                                youtubeURL = song.youtubeUrl ?: "",
                                thumbnailUrl = song.thumbnailUrl ?: ""
                            )
                        }

                        SongPresentationType.TEASER -> {
                            SongCard(
                                isOddRow = song.isOddRow,
                                text = song.text,
                                youtubeURL = song.youtubeUrl ?: "",
                                thumbnailUrl = song.thumbnailUrl ?: ""
                            )
                        }

                        SongPresentationType.INFO -> {
                            InfoSongCard(
                                isOddRow = song.isOddRow,
                                text = song.text
                            )
                        }
                    }

                }
            } else {
                item {
                    SongsEmptyCard()
                }
            }
        }
    }
}