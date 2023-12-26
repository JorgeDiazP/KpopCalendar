package com.jorgediazp.kpopcalendar.home.liked.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.liked.presentation.component.LikedSongLazyColumn
import com.jorgediazp.kpopcalendar.home.liked.presentation.model.LikedScreenState
import com.jorgediazp.kpopcalendar.home.search.presentation.component.NoResultsView

@Composable
fun LikedScreen(viewModel: LikedViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

    Screen {
        when (state.value) {
            is LikedScreenState.ShowNothing -> {
                // nothing to do
            }

            is LikedScreenState.ShowEmpty -> {
                NoResultsView()
            }

            is LikedScreenState.ShowError -> {
                ErrorView()
            }

            is LikedScreenState.ShowSongList -> {
                LikedSongLazyColumn(
                    songList = (state.value as LikedScreenState.ShowSongList).songList,
                    onLikeClicked = {})
            }
        }
    }
}