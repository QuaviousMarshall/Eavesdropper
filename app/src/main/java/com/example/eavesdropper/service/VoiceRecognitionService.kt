package com.example.eavesdropper.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.eavesdropper.R
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VoiceRecognitionService : Service() {

    @Inject
    lateinit var controller: SpeechRecognizerController

    override fun onCreate() {
        super.onCreate()
        startForeground(1, notification())
        controller.start()
    }

    override fun onDestroy() {
        controller.stop()
        controller.destroy()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun notification(): Notification {
        val channelId = "voice_recognition"

        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                channelId,
                "Voice recognition",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.mic)
            .setContentTitle("Трон слушает")
            .setContentText("Распознавание речи активно")
            .build()
    }
}



