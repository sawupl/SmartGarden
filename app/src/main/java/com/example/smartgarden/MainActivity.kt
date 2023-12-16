package com.example.smartgarden

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartgarden.notification.NotificationReceiver
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scheduleNotification()
    }

    private fun scheduleNotification() {
        // Получение AlarmManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Создание Intent для BroadcastReceiver
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Установка повторяющегося будильника на каждый день в 8:00
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val alarmStartTime = calendar.timeInMillis

        val interval = AlarmManager.INTERVAL_DAY // повтор каждый день

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmStartTime,
            interval,
            pendingIntent
        )
    }
}