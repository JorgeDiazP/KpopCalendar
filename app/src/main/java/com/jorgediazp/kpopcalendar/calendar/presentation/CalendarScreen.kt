package com.jorgediazp.kpopcalendar.calendar.presentation

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.jorgediazp.kpopcalendar.calendar.presentation.component.CalendarPicker
import com.jorgediazp.kpopcalendar.calendar.presentation.component.CalendarSongsLazyColumn
import com.jorgediazp.kpopcalendar.calendar.presentation.component.CalendarTopAppBar
import com.jorgediazp.kpopcalendar.calendar.presentation.component.NotificationsPermissionDialog
import com.jorgediazp.kpopcalendar.calendar.presentation.model.CalendarScreenBackgroundState
import com.jorgediazp.kpopcalendar.calendar.presentation.model.CalendarScreenForegroundState
import com.jorgediazp.kpopcalendar.common.presentation.ui.ComposeUtils.Companion.isScrollingUp
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.LoadingView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen

@Composable
fun CalendarScreen(
    listState: LazyListState,
    showSnackBar: (text: String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val backgroundState = viewModel.backgroundState.collectAsStateWithLifecycle()
    val foregroundState = viewModel.foregroundState.collectAsStateWithLifecycle()
    val showSnackBarEvent = viewModel.showSnackBarEvent.collectAsStateWithLifecycle()

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
                            onLikeClicked = { song -> viewModel.insertOrDeleteLikedSong(song) }
                        )
                    }
                }

                is CalendarScreenBackgroundState.ShowError -> {
                    ErrorView(onTryAgainClick = { viewModel.loadDateList(backgroundState.value.selectedDateMillis) })
                }

                is CalendarScreenBackgroundState.ShowDefaultError -> {
                    ErrorView(onTryAgainClick = { viewModel.loadData() })
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

            is CalendarScreenForegroundState.ShowNotificationsPermissionDialog -> {
                NotificationsPermissionDialog(
                    onConfirm = {
                        viewModel.foregroundState.value =
                            CalendarScreenForegroundState.RequestNotificationsPermissionDialog
                    },
                    onCancel = { viewModel.onCancelNotificationsPermission() })
            }

            is CalendarScreenForegroundState.RequestNotificationsPermissionDialog -> {
                RequestNotificationsPermission(viewModel = viewModel)
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

    showSnackBarEvent.value.getContentIfNotHandled()?.let { textResId ->
        showSnackBar(stringResource(textResId))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationsPermission(viewModel: CalendarViewModel) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(key1 = Unit) {
        viewModel.foregroundState.value = CalendarScreenForegroundState.ShowNothing
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        viewModel.loadData()
    } else {
        viewModel.onCancelNotificationsPermission()
    }
}