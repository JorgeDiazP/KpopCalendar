package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.presentation.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.home.search.presentation.component.NoResultsView
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchSongLazyColumn
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchTextField
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Screen {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                    SearchSongLazyColumn(songList = (state.value as SearchScreenState.ShowSongList).songList)
                }
            }
        }

    }
}