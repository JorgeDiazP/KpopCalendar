package com.jorgediazp.kpopcomebacks.splash.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jorgediazp.kpopcomebacks.splash.presentation.model.SplashActivityState
import com.jorgediazp.kpopcomebacks.splash.presentation.model.SplashConfigEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.EnumMap
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    val state = MutableStateFlow<SplashActivityState>(SplashActivityState.ShowNothing)
    private val configMap = EnumMap(SplashConfigEnum.values().associateWith { false })

    fun loadData() {
        configMap.forEach { entry ->
            if (!entry.value) {
                when (entry.key) {
                    SplashConfigEnum.FETCH_REMOTE_CONFIG -> {
                        fetchRemoteConfig()
                    }

                    SplashConfigEnum.CONFIGURATION_FINISHED -> {
                        state.value = SplashActivityState.LaunchMainActivity
                    }

                    else -> {
                        // nothing to do
                    }
                }
            }
        }
    }

    private fun fetchRemoteConfig() {
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
        configMap[SplashConfigEnum.FETCH_REMOTE_CONFIG] = true
        loadData()
    }
}