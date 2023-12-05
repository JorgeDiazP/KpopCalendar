package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.theme.LocalCustomColorsPalette

@Composable
fun DayCard(
    text: String,
    isToday: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(0.dp),
    ) {
        val textModifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        if (isToday) {
            textModifier.background(
                color = LocalCustomColorsPalette.current.surfaceVariantSecondary,
                shape = RoundedCornerShape(8.dp)
            )
        }
        Text(
            text = text,
            modifier = textModifier,
            style = MaterialTheme.typography.titleMedium
        )
    }
}