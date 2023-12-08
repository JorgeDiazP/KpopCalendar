package com.jorgediazp.kpopcalendar.main.calendar.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.theme.KpopCalendarTheme

@Composable
fun SongCard(
    isOdd: Boolean,
    text: String,
    youtubeURL: String,
    thumbnailUrl: String,
) {
    var loadVideo by remember { mutableStateOf(false) }
    val cardColor =
        if (isOdd) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer
    val buttonColor =
        if (isOdd) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary

    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )
            if (loadVideo) {
                YoutubeVideoPlayer(youtubeURL = youtubeURL, backgroundColor = cardColor)
            } else {
                ThumbnailView(
                    thumbnailUrl = thumbnailUrl,
                    cardColor = cardColor,
                    buttonColor = buttonColor,
                    onPlayClick = { loadVideo = true })
            }
        }
    }
}

@Preview
@Composable
private fun SongCardPreview() {
    KpopCalendarTheme {
        SongCard(
            isOdd = true,
            text = "Stray Kids - Maniac",
            youtubeURL = "https://www.youtube.com/watch?v=OvioeS1ZZ7o",
            thumbnailUrl = "https://img.youtube.com/vi/OvioeS1ZZ7o/0.jpg"
        )
    }
}