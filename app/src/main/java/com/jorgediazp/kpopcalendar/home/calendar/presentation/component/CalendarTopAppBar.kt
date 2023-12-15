package com.jorgediazp.kpopcalendar.home.calendar.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.EditCalendar
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
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopAppBar(
    title: String,
    todayDayString: String,
    onShowCalendarClick: () -> Unit,
    onGoToTodayClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconButton(
                onClick = { onShowCalendarClick() },
            ) {
                Icon(
                    imageVector = Icons.Filled.EditCalendar,
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { onGoToTodayClick() },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Text(text = todayDayString, style = MaterialTheme.typography.labelSmall)
            }
        }
    )
}

@Preview
@Composable
fun CalendarTopAppBarPreview() {
    KpopCalendarTheme {
        CalendarTopAppBar(
            title = "Noviembre",
            todayDayString = "31",
            onShowCalendarClick = {},
            onGoToTodayClick = {})
    }
}