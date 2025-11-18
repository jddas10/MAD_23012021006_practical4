package com.example.practical_4

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AlarmService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    // Unique IDs for the notification and its channel
    private val CHANNEL_ID = "AlarmServiceChannel"
    private val NOTIFICATION_ID = 1

    override fun onBind(intent: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // This is only required for Android 8.0 (Oreo) and above
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("Service1")

        when (action) {
            "Start" -> startAlarm()
            "Stop" -> stopAlarm()
        }

        // If the service is killed, it will be automatically restarted
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun startAlarm() {
        // Create a notification and start the service in the foreground
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        // Stop and release any previous player instance to avoid overlaps
        stopAndReleasePlayer()

        // Create and start a new MediaPlayer instance for the alarm sound
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun stopAlarm() {
        // Stop the media player
        stopAndReleasePlayer()

        // Stop the foreground service and remove the notification
        stopForeground(STOP_FOREGROUND_REMOVE)

        // Stop the service itself, as its job is done
        stopSelf()
    }

    private fun stopAndReleasePlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun createNotificationChannel() {
        // Notification Channels are only available on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel", // Name visible to the user in settings
                NotificationManager.IMPORTANCE_LOW // Use LOW to avoid sound/vibration from the notification itself
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        // Make sure you have an 'ic_alarm' drawable in your res/drawable folder
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Alarm Playing")
            .setContentText("Your alarm is ringing.")
            .setSmallIcon(R.drawable.ic_alarm)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure resources are always released when the service is destroyed
        stopAndReleasePlayer()
    }
}
