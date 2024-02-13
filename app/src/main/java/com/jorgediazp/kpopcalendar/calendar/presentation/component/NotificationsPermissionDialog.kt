package com.jorgediazp.kpopcalendar.calendar.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme

@Composable
fun NotificationsPermissionDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.calendar_notifications_permission_title))
        },
        text = {
            Text(text = stringResource(R.string.calendar_notifications_permission_message))
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(text = stringResource(android.R.string.cancel))
            }
        }
    )
}

@Composable
@Preview
fun NotificationsPermissionDialogPreview() {
    KpopCalendarTheme {
        NotificationsPermissionDialog(onConfirm = {}) {
        }
    }
}