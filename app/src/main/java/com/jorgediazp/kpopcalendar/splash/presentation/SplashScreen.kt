package com.jorgediazp.kpopcalendar.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.R
import com.jorgediazp.kpopcalendar.common.presentation.theme.LocalCustomColorsPalette
import com.jorgediazp.kpopcalendar.common.presentation.ui.Screen
import com.jorgediazp.kpopcalendar.splash.presentation.model.SplashActivityState

@Composable
fun SplashScreen(
    onConfigurationFinished: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        if (!viewModel.dataLoaded) {
            viewModel.loadData()
        }
    }

    Screen {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp).padding(top = 24.dp),
                strokeWidth = 8.dp,
                color = LocalCustomColorsPalette.current.surfaceVariantSecondary,
            )
        }

        when (state.value) {
            SplashActivityState.ShowNothing -> {
                // nothing to do
            }

            SplashActivityState.LaunchMainActivity -> {
                onConfigurationFinished()
            }
        }
    }
}

@Composable
@Preview
private fun SplashScreenPreview() {
    SplashScreen(onConfigurationFinished = {})
}