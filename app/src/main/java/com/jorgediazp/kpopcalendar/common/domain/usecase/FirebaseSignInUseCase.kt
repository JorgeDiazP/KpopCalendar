package com.jorgediazp.kpopcalendar.common.domain.usecase

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.crashlytics.crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseSignInUseCase @Inject constructor() {

    suspend fun signIn(): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                if (Firebase.auth.currentUser != null) {
                    true
                } else {
                    Firebase.auth.signInAnonymously().await().user != null
                }
            }
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            false
        }

    }
}