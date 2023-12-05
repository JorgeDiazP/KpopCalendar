package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.theme.KpopComebacksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopAppBar(title: String, onShowCalendarClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconButton(
                onClick = { onShowCalendarClick() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.EditCalendar,
                    contentDescription = null,
                )
            }
        }
    )
}

@Preview
@Composable
fun CalendarTopAppBarPreview() {
    KpopComebacksTheme {
        CalendarTopAppBar(title = "Noviembre", onShowCalendarClick = {})
    }
}