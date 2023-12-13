package com.jorgediazp.kpopcalendar.home.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgediazp.kpopcalendar.common.ui.Screen
import com.jorgediazp.kpopcalendar.home.search.presentation.component.SearchTextField

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    Screen {
        Column {
            SearchTextField()
            LazyColumn { }
        }

    }
}