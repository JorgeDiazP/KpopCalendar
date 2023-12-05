package com.jorgediazp.kpopcalendar.splash.presentation.model

sealed class SplashActivityState {

    data object ShowNothing : SplashActivityState()

    data object LaunchMainActivity : SplashActivityState()
}
