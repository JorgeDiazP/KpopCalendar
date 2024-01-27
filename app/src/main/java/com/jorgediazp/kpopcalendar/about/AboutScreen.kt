package com.jorgediazp.kpopcalendar.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    val context = LocalContext.current
    Screen {
        Column {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
                title = {
                    Text(
                        text = stringResource(R.string.about_top_app_bar),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.about_text),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(32.dp)
                )
                Button(onClick = { launchPlayStore(context) }) {
                    Text(text = stringResource(id = R.string.about_button))
                }
            }
        }
    }
}

private fun launchPlayStore(context: Context) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=${context.packageName}")
        )
        context.startActivity(intent)
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
    }

}

@Preview
@Composable
private fun AboutScreenPreview() {
    KpopCalendarTheme {
        AboutScreen()
    }
}