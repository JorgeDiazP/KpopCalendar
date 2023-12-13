package com.jorgediazp.kpopcalendar.splash.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcalendar.common.ui.Screen
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