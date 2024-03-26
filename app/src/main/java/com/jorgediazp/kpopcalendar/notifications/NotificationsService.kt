package com.jorgediazp.kpopcalendar.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jorgediazp.kpopcalendar.R
import kotlin.random.Random

class NotificationsService : FirebaseMessagingService() {

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        try {
            showNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
        }
    }

    private fun showNotification(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            getString(R.string.firebase_notifications_channel),
            getString(R.string.firebase_notifications_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder =
            NotificationCompat.Builder(this, getString(R.string.firebase_notifications_channel))
        notificationBuilder
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(),
                    PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher_round))
            .setLargeIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher_round).toIcon(this  ))
            .setContentTitle(title)
            .setContentText(body)
        notificationManager.notify(Random.nextInt(), notificationBuilder.build());
    }
}