package com.jorgediazp.kpopcomebacks.splash.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jorgediazp.kpopcomebacks.main.common.presentation.MainActivity
import com.jorgediazp.kpopcomebacks.splash.presentation.model.SplashActivityState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = viewModel.state.collectAsStateWithLifecycle()
            when (state.value) {
                SplashActivityState.ShowNothing -> {
                    // nothing to do
                }

                SplashActivityState.LaunchMainActivity -> {
                    launchMainActivity()
                }
            }
        }

        viewModel.loadData()
    }

    private fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}