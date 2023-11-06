package com.jorgediazp.kpopcomebacks.splash.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
            CustomCard(text = "Stray Kids")
            CustomCard(text = "aespa")
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Boton")
            }
        }
    }
}