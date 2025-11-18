package com.example.practical_4

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var btnSetAlarm: MaterialButton
    private lateinit var btnStopAlarm: MaterialButton
    private lateinit var cancelCard: MaterialCardView


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askNotificationPermission()

        timePicker = findViewById(R.id.reminderTime)
        btnSetAlarm = findViewById(R.id.create_AlarmBtn)
        btnStopAlarm = findViewById(R.id.cancel_AlarmBtn)
        cancelCard = findViewById(R.id.cancel_alarm_card_view)

        cancelCard.visibility = View.GONE

        btnSetAlarm.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val hour = timePicker.hour
                val minute = timePicker.minute
                setAlarm(hour, minute)
            } else {
                Toast.makeText(this, "This feature requires Android 12 or higher.", Toast.LENGTH_LONG).show()
            }
        }

        btnStopAlarm.setOnClickListener {
            stopAlarm()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        if (alarmManager.canScheduleExactAlarms()) {
            val alarmCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            val intent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
                putExtra("Service1", "Start")
            }

            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                234324243,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmCalendar.timeInMillis,
                pendingIntent
            )

            val timeStr = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(alarmCalendar.time)
            Toast.makeText(this, "Alarm set for $timeStr", Toast.LENGTH_LONG).show()
            cancelCard.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Permission is required to set exact alarms.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    private fun stopAlarm() {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            234324243,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        intent.putExtra("Service1", "Stop")
        sendBroadcast(intent)

        Toast.makeText(this, "Alarm Stopped", Toast.LENGTH_SHORT).show()
        cancelCard.visibility = View.GONE
    }
}

