package com.jorgediazp.kpopcalendar.home.calendar.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.home.common.presentation.ui.component.SongCard
import com.jorgediazp.kpopcalendar.home.calendar.presentation.model.DatePresentationModel
import com.jorgediazp.kpopcalendar.home.common.presentation.model.SongPresentationType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarSongsLazyColumn(listState: LazyListState, selectedDateIndex: Int, dateList: List<DatePresentationModel>) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(32.dp)
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

                        SongPresentationType.UNRELEASED_TEASER -> {
                            SongCard(
                                isOddRow = song.isOddRow,
                                text = song.text,
                                youtubeURL = song.youtubeUrl ?: "",
                                thumbnailUrl = song.thumbnailUrl ?: ""
                            )
                        }

                        SongPresentationType.UNRELEASED_INFO -> {
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
        item {
            Spacer(modifier = Modifier.fillMaxWidth().height(64.dp))
        }
    }

    LaunchedEffect(key1 = dateList) {
        // This is performed to show TopBar and NavigationBar because are hidden when scroll down
        listState.scrollToItem(selectedDateIndex + 1)
        listState.animateScrollToItem(selectedDateIndex)
    }
}