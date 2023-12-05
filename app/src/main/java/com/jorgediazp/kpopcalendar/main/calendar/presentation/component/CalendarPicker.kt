package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPicker(
    minTimestamp: Long,
    maxTimestamp: Long,
    onAccept: (selectedDateMillis: Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        confirmButton = {
            TextButton(onClick = {
                onAccept(state.selectedDateMillis)
            }) {
                Text(text = stringResource(id = R.string.accept))
            }
        },
        onDismissRequest = {
            onCancel()
        },
        dismissButton = {
            TextButton(onClick = {
                onCancel()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }) {
        DatePicker(
            state = state,
            dateValidator = { timestamp -> timestamp in minTimestamp..maxTimestamp })
    }
}

@Preview
@Composable
private fun CalendarPickerPreview() {
    KpopCalendarTheme {
        CalendarPicker(
            minTimestamp = 0,
            maxTimestamp = Long.MAX_VALUE,
            onAccept = {},
            onCancel = {})
    }
}