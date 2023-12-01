package com.jorgediazp.kpopcomebacks.main.calendar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcomebacks.common.theme.KpopComebacksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPicker(onAccept: (selectedDateMillis: Long?) -> Unit, onCancel: () -> Unit) {
    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(0.6f)),
            contentAlignment = Alignment.Center
        ) {
            DatePickerDialog(modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {
                    openDialog.value = false
                    onCancel()
                },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        onAccept(state.selectedDateMillis)
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        onCancel()
                    }) {
                        Text(text = "CANCEL")
                    }
                }) {
                DatePicker(state = state)
            }
        }
    }
}

@Preview
@Composable
private fun CalendarPickerPreview() {
    KpopComebacksTheme {
        CalendarPicker({}, {})
    }
}