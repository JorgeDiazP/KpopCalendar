package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.common.theme.LocalCustomColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPicker(
    initialSelectedDateMillis: Long,
    yearRange: IntRange,
    minTimestamp: Long,
    maxTimestamp: Long,
    onAccept: (selectedDateMillis: Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        yearRange = yearRange
    )

    DatePickerDialog(
        confirmButton = {
            TextButton(onClick = {
                onAccept(state.selectedDateMillis)
            }) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        onDismissRequest = {
            onCancel()
        },
        dismissButton = {
            TextButton(onClick = {
                onCancel()
            }) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }) {
        DatePicker(
            state = state,
            dateValidator = { timestamp -> timestamp in minTimestamp..maxTimestamp },
            colors = DatePickerDefaults.colors(
                todayContentColor = LocalCustomColorsPalette.current.complementary,
                todayDateBorderColor = LocalCustomColorsPalette.current.complementary
            )
        )
    }
}

@Preview
@Composable
private fun CalendarPickerPreview() {
    KpopCalendarTheme {
        CalendarPicker(
            initialSelectedDateMillis = 0,
            yearRange = IntRange(2020, 2024),
            minTimestamp = 0,
            maxTimestamp = Long.MAX_VALUE,
            onAccept = {},
            onCancel = {})
    }
}