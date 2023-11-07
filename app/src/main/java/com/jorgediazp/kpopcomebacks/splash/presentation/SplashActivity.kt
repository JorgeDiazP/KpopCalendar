package com.jorgediazp.kpopcomebacks.splash.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jorgediazp.kpopcomebacks.common.Screen

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorTest()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorTest() {
    Screen {
        Column {
            TitleCard(text = "November") {
                TitleCard(text = "2 November 2023") {
                    SongCard(text = "Stray Kids", true)
                    SongCard(text = "aespa", false)
                    SongCard(text = "ATEEZ", true)
                }
            }
        }
    }
}