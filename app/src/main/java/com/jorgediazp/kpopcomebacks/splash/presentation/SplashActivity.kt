package com.jorgediazp.kpopcomebacks.splash.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.jorgediazp.kpopcomebacks.main.common.presentation.MainActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}