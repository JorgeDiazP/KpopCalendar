package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.search.presentation.component.NoResultsView
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchSongLazyColumn
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchTextField
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState

@Composable
fun SearchScreen(
    showSnackBar: (text: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val showSnackBarEvent = viewModel.showSnackBarEvent.collectAsStateWithLifecycle()

    Screen {
        Column {
            SearchTextField(onValueChange = { query -> viewModel.loadSongListByQuery(query) })

            when (state.value) {
                is SearchScreenState.ShowNothing -> {
                    // nothing to do
                }

                is SearchScreenState.ShowError -> {
                    ErrorView()
                }

                is SearchScreenState.ShowNoResults -> {
                    NoResultsView()
                }

                is SearchScreenState.ShowSongList -> {
                    SearchSongLazyColumn(
                        songList = (state.value as SearchScreenState.ShowSongList).songList,
                        onLikeClicked = { song -> viewModel.insertLikedSong(song) })
                }
            }
        }

    }

    showSnackBarEvent.value.getContentIfNotHandled()?.let { textResId ->
        showSnackBar(stringResource(textResId))
    }
}