package com.jorgediazp.kpopcalendar.home.liked.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.liked.presentation.component.LikedSongLazyColumn
import com.jorgediazp.kpopcalendar.home.liked.presentation.model.LikedScreenState
import com.jorgediazp.kpopcalendar.common.presentation.ui.NoResultsView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedScreen(showSnackBar: (text: String) -> Unit, viewModel: LikedViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val showSnackBarEvent = viewModel.showSnackBarEvent.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

    Screen {
        Column {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
                title = {
                    Text(
                        text = stringResource(R.string.liked_top_app_bar),
                        style = MaterialTheme.typography.titleMedium
                    )
                })

            when (state.value) {
                is LikedScreenState.ShowNothing -> {
                    // nothing to do
                }

                is LikedScreenState.ShowEmpty -> {
                    NoResultsView(text = stringResource(id = R.string.liked_empty_view))
                }

                is LikedScreenState.ShowError -> {
                    ErrorView()
                }

                is LikedScreenState.ShowSongList -> {
                    LikedSongLazyColumn(
                        songList = (state.value as LikedScreenState.ShowSongList).songList,
                        onLikeClicked = { song -> viewModel.deleteLikedSong(song) })
                }
            }
        }
    }

    showSnackBarEvent.value.getContentIfNotHandled()?.let { textResId ->
        showSnackBar(stringResource(textResId))
    }
}