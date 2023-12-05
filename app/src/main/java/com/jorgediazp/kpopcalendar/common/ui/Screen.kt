package com.jorgediazp.kpopcalendar.common.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    KpopCalendarTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}