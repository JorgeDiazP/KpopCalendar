package com.jorgediazp.kpopcalendar.home.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgediazp.kpopcalendar.common.ui.Screen

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = viewModel) {
        //viewModel.loadData()
    }

    Screen {
        Text(text = "Search")
    }
}