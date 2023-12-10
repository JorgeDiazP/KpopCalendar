package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.common.theme.LocalCustomColorsPalette

@Composable
fun SongsEmptyCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            text = stringResource(id = R.string.calendar_songs_empty),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun SongsEmptyCardPreview() {
    KpopCalendarTheme {
        SongsEmptyCard()
    }
}