package com.example.practical_4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val str1 = intent.getStringExtra("Service1")

        if (str1 == "Start" || str1 == "Stop") {
            val intentService = Intent(context, AlarmService::class.java)
            intentService.putExtra("Service1", str1)

            if (str1 == "Start") {
                // On modern Android, you MUST use startForegroundService from a receiver
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intentService)
                } else {
                    context.startService(intentService)
                }
            } else if (str1 == "Stop") {
                // To stop the service cleanly, you must also use startService.
                // This delivers the "Stop" command to the service's onStartCommand.
                context.startService(intentService)
            }
        }
    }
}
