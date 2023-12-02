package com.jorgediazp.kpopcomebacks.main.calendar.presentation.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcomebacks.R
import com.jorgediazp.kpopcomebacks.common.theme.KpopComebacksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPicker(onAccept: (selectedDateMillis: Long?) -> Unit, onCancel: () -> Unit) {
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
        DatePicker(state = state)
    }
}

@Preview
@Composable
private fun CalendarPickerPreview() {
    KpopComebacksTheme {
        CalendarPicker({}, {})
    }
}