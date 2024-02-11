package com.jorgediazp.kpopcalendar.common.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgediazp.kpopcalendar.common.presentation.theme.KpopCalendarTheme
import com.jorgediazp.kpopcalendar.common.presentation.theme.LocalCustomColorsPalette

@Composable
fun KpopCalendarSnackBar(message: String) {
    Snackbar(
        containerColor = LocalCustomColorsPalette.current.complementaryContainer,
        contentColor = LocalCustomColorsPalette.current.onComplementaryContainer,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun KpopCalendarSnackBarPreview() {
    KpopCalendarTheme {
        KpopCalendarSnackBar("Se ha añadido la canción")
    }
}