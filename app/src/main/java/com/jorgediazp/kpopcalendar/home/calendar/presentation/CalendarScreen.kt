package com.jorgediazp.kpopcalendar.home.calendar.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.presentation.ui.ComposeUtils.Companion.isScrollingUp
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.LoadingView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcalendar.home.calendar.presentation.component.CalendarSongsLazyColumn
import com.jorgediazp.kpopcalendar.home.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcalendar.home.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.home.calendar.presentation.model.CalendarScreenForegroundState

@Composable
fun CalendarScreen(listState: LazyListState, viewModel: CalendarViewModel = hiltViewModel()) {
    val backgroundState = viewModel.backgroundState.collectAsStateWithLifecycle()
    val foregroundState = viewModel.foregroundState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

    Screen {
        Column {
            AnimatedVisibility(visible = listState.isScrollingUp()) {
                CalendarTopAppBar(
                    title = backgroundState.value.topBarTitle,
                    todayDayString = backgroundState.value.todayDayString,
                    onShowCalendarClick = { viewModel.loadDatePicker(backgroundState.value.selectedDateMillis) },
                    onGoToTodayClick = { viewModel.goToToday() }
                )
            }
            when (backgroundState.value) {
                is CalendarScreenBackgroundState.ShowNothing -> {
                    // nothing to do
                }

                is CalendarScreenBackgroundState.ShowDateList -> {
                    (backgroundState.value as CalendarScreenBackgroundState.ShowDateList).let { state ->
                        CalendarSongsLazyColumn(
                            listState = listState,
                            selectedDateIndex = state.selectedDateIndex,
                            dateList = state.dateList,
                            onLikeClicked = { song -> viewModel.insertLikedSong(song) }
                        )
                    }
                }

                is CalendarScreenBackgroundState.ShowError -> {
                    ErrorView(onTryAgainClick = { viewModel.loadDateList(backgroundState.value.selectedDateMillis) })
                }
            }
        }
        when (foregroundState.value) {
            is CalendarScreenForegroundState.ShowNothing -> {
                // nothing to do
            }

            is CalendarScreenForegroundState.ShowLoading -> {
                LoadingView()
            }

            is CalendarScreenForegroundState.ShowCalendarPicker -> {
                (foregroundState.value as CalendarScreenForegroundState.ShowCalendarPicker).let { state ->
                    CalendarPicker(
                        initialSelectedDateMillis = state.initialSelectedDateMillis,
                        yearRange = state.yearRange,
                        minTimestamp = state.minTimestamp,
                        maxTimestamp = state.maxTimestamp,
                        onAccept = { selectedDateMillis -> viewModel.loadDateList(selectedDateMillis) },
                        onCancel = {
                            viewModel.foregroundState.value =
                                CalendarScreenForegroundState.ShowNothing
                        })
                }
            }
        }
    }
}