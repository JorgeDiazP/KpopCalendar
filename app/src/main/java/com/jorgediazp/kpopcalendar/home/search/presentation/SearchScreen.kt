package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.common.presentation.ui.NoResultsView
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

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

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
                    NoResultsView(text = stringResource(id = R.string.search_no_results))
                }

                is SearchScreenState.ShowSongList -> {
                    SearchSongLazyColumn(
                        songList = (state.value as SearchScreenState.ShowSongList).songList,
                        onLikeClicked = { song -> viewModel.insertOrDeleteLikedSong(song) })
                }
            }
        }

    }

    showSnackBarEvent.value.getContentIfNotHandled()?.let { textResId ->
        showSnackBar(stringResource(textResId))
    }
}