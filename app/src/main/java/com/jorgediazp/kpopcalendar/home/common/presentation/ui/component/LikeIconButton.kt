package com.jorgediazp.kpopcalendar.home.common.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.common.presentation.theme.LocalCustomColorsPalette

@Composable
fun LikeIconButton(
    liked: Boolean,
    onLikeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { onLikeClicked() },
    ) {
        Icon(
            imageVector = if (liked) Icons.TwoTone.Favorite else Icons.Outlined.FavoriteBorder,
            tint = if (liked) LocalCustomColorsPalette.current.complementary else LocalContentColor.current.copy(
                0.2f
            ),
            modifier = Modifier.size(32.dp),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun LikeIconButtonPreview() {
    KpopCalendarTheme {
        LikeIconButton(liked = true, onLikeClicked = {})
    }
}