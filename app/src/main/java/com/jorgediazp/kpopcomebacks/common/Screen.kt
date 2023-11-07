package com.jorgediazp.kpopcomebacks.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorgediazp.kpopcomebacks.common.theme.KpopComebacksTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    KpopComebacksTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}