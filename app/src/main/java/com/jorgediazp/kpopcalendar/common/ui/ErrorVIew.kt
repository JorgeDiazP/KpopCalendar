package com.jorgediazp.kpopcalendar.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme

@Composable
fun ErrorView(onTryAgainClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.error_text),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(32.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = { onTryAgainClick() }) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    }
}

@Preview
@Composable
private fun ErrorViewPreview() {
    KpopCalendarTheme {
        ErrorView(onTryAgainClick = {})
    }
}