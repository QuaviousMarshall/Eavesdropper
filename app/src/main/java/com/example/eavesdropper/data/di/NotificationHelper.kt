package com.example.eavesdropper.data.di

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.eavesdropper.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val channelId = "tron_answers_channel"

    init {
        createChannel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Tron Answers",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Уведомления с ответами на вопросы"
            }

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showAnswerNotification(question: String, answer: String) {

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.question_mark_circle_svg_icon)
            .setContentText(answer)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Ответ: $answer")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notification)
    }
}
