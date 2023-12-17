package com.example.smartgarden

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartgarden.databinding.ActivityMainBinding
import com.example.smartgarden.notification.NotificationService
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.panel.visibility = View.VISIBLE
                    binding.back.visibility = View.GONE
                    binding.title.text = "Вход"
                }
                R.id.registrationFragment -> {
                    binding.panel.visibility = View.VISIBLE
                    binding.back.visibility = View.GONE
                    binding.title.text = "Регистрация"
                }
                R.id.mainFragment -> {
                    binding.panel.visibility = View.VISIBLE
                    binding.back.visibility = View.GONE
                    binding.title.text = "Список грядок"
                }
                R.id.editGardenFragment -> {
                    binding.panel.visibility = View.GONE
                }
                R.id.plantFragment -> {
                    binding.panel.visibility = View.VISIBLE
                    binding.title.text = "Список растений"
                    binding.back.visibility = View.VISIBLE
                    binding.back.setOnClickListener{
                        navController.popBackStack()
                    }
                }
                R.id.gardenFragment -> {
                    binding.panel.visibility = View.GONE
                }
                R.id.plantInfoFragment -> {
                    binding.panel.visibility = View.VISIBLE
                    binding.title.text = "Информация о растении"
                    binding.back.visibility = View.VISIBLE
                    binding.back.setOnClickListener{
                        navController.popBackStack()
                    }
                }
            }
        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS,
                POST_NOTIFICATIONS_PERMISSION_CODE)
        }
        else {
            scheduleNotification(this)
        }
    }

    override fun onResume() {
        super.onResume()
        scheduleNotification(this)
    }

    private fun scheduleNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, NotificationService::class.java).let { notificationIntent ->
            PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Повторяем уведомление каждый день
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    private fun requestPermission(context: Context, permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (context as Activity),
                    arrayOf(permission),
                    requestCode)
            }
        else {
            scheduleNotification(this)
        }

    }
    companion object {
        const val POST_NOTIFICATIONS_PERMISSION_CODE = 0
    }
}