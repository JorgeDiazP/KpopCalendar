package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.ui.ErrorView
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchTextField
import com.jorgediazp.kpopcalendar.home.search.presentation.model.SearchScreenState

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Screen {
        Column {
            SearchTextField(onValueChange = { query -> viewModel.loadSongListByQuery(query) })

            when (state.value) {
                SearchScreenState.ShowNothing -> {
                    // nothing to do
                }

                SearchScreenState.ShowError -> {
                    ErrorView()
                }

                SearchScreenState.ShowNoResults -> {
                    TODO()
                }
            }
        }

    }
}