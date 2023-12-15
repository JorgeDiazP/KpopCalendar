package com.jorgediazp.kpopcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jorgediazp.kpopcalendar.home.common.presentation.ui.screen.HomeScreen
import com.jorgediazp.kpopcalendar.splash.presentation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KpopCalendarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var destination by remember { mutableStateOf("splash") }

            when (destination) {
                "splash" -> {
                    SplashScreen(onConfigurationFinished = { destination = "home" })
                }

                "home" -> {
                    HomeScreen()
                }
            }
        }
    }
}