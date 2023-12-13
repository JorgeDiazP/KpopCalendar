package com.jorgediazp.kpopcalendar.home.calendar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.theme.LocalCustomColorsPalette
import com.jorgediazp.kpopcalendar.common.ui.Screen

@Composable
fun DateCard(
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
        var textModifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
        if (isToday) {
            textModifier = textModifier.then(
                Modifier
                    .background(
                        color = LocalCustomColorsPalette.current.complementaryContainer,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = LocalCustomColorsPalette.current.complementary,
                        shape = RoundedCornerShape(24.dp)
                    )
            )
        }
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = text,
                modifier = textModifier.then(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)),
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Preview
@Composable
private fun DateCardPreview() {
    Screen {
        DateCard(text = "Lunes 10", isToday = true)
    }
}