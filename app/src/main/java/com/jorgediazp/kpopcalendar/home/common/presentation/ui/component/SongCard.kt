package com.jorgediazp.kpopcalendar.home.common.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.home.calendar.presentation.component.ThumbnailView
import com.jorgediazp.kpopcalendar.home.calendar.presentation.component.YoutubeVideoPlayer

@Composable
fun SongCard(
    isOddRow: Boolean,
    text: String,
    youtubeURL: String,
    thumbnailUrl: String,
    liked: Boolean = false,
    onLikeClicked: (() -> Unit)? = null
) {
    var loadVideo by remember { mutableStateOf(false) }

    val color =
        if (isOddRow) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
    val backgroundColor =
        if (isOddRow) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer
    val onContainerColor =
        if (isOddRow) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onTertiaryContainer

    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, color)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                    color = onContainerColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                )
                if (onLikeClicked != null) {
                    LikeIconButton(
                        liked = liked,
                        onLikeClicked = onLikeClicked,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            if (loadVideo) {
                YoutubeVideoPlayer(youtubeURL = youtubeURL, backgroundColor = backgroundColor)
            } else {
                ThumbnailView(
                    thumbnailUrl = thumbnailUrl,
                    cardColor = backgroundColor,
                    buttonColor = color,
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
            isOddRow = true,
            text = "Stray Kids - Maniac 1111111111111111111111111111111111111111111111111111111111111111",
            youtubeURL = "https://www.youtube.com/watch?v=OvioeS1ZZ7o",
            thumbnailUrl = "https://img.youtube.com/vi/OvioeS1ZZ7o/0.jpg",
            onLikeClicked = {}
        )
    }
}