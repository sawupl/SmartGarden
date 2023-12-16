package com.example.smartgarden.notification

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.smartgarden.MainActivity
import com.example.smartgarden.R
import java.util.*

class NotificationService : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "NotificationServiceChannel"
        private const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent?) {
        // Выполняем задачи, например, отправляем уведомление
        sendNotification(context)
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(context: Context) {
        // Создаем канал уведомлений (необходимо для Android 8.0 и новее)
        createNotificationChannel(context)

        // Создаем уведомление
        val notification = createNotification(context)

        // Отправляем уведомление
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotification(context: Context): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Daily Notification")
            .setContentText("It's 8:00 AM!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(ContextCompat.getColor(context, R.color.blue))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Notification Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
