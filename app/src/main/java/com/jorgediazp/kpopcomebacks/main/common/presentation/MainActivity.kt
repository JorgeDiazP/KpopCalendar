package com.jorgediazp.kpopcomebacks.main.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.jorgediazp.kpopcomebacks.common.ui.Screen
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.model.ComebackVO
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.DayCard
import com.jorgediazp.kpopcomebacks.main.calendar.presentation.component.SongCard
import com.jorgediazp.kpopcomebacks.main.common.presentation.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel.loadData()

        setContent {
            MainScreen()
        }
    }
}