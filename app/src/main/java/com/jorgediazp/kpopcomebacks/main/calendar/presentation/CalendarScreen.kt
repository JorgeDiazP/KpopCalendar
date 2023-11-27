package com.jorgediazp.kpopcomebacks.main.calendar.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jorgediazp.kpopcomebacks.common.ui.Screen

@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    Screen {
        Text(text = "Calendar")
        viewModel.loadData()
    }
}