package com.jorgediazp.kpopcomebacks.splash.presentation.model

sealed class SplashActivityState {

    data object ShowNothing : SplashActivityState()

    data object LaunchMainActivity : SplashActivityState()
}
