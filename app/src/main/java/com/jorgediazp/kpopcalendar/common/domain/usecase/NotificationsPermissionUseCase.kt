package com.jorgediazp.kpopcalendar.common.domain.usecase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationsPermissionUseCase @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val SP_NAME = "notifications_sp"
        private val FLAG_KEY = "request_notifications_flag"
    }

    suspend fun requestNotificationsPermission(): Boolean {
        return withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                sharedPreferences.getBoolean(FLAG_KEY, true)
            } else {
                false
            }
        }
    }

    suspend fun updateNotificationsPermissionsFlag() {
        withContext(Dispatchers.IO) {
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean(FLAG_KEY, false).commit()
        }
    }
}