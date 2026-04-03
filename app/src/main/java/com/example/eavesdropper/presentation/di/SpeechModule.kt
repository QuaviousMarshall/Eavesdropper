package com.example.eavesdropper.presentation.di

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.example.eavesdropper.data.detector.AndroidSpeechRecognizer
import com.example.eavesdropper.domain.detector.QuestionDetector
import com.example.eavesdropper.data.detector.QuestionDetectorImpl
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import com.example.eavesdropper.data.detector.SystemSoundController
import com.example.eavesdropper.data.factory.AiRepositoryFactory
import com.example.eavesdropper.data.repository.GigaChatRepository
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpeechModule {

    @Provides
    @Singleton
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun provideQuestionDetector(
        repository: TronRepository,
        factory: AiRepositoryFactory,
        notificationHelper: NotificationHelper,
        sessionManager: SessionManager
    ): QuestionDetector =
        QuestionDetectorImpl { question ->
            CoroutineScope(Dispatchers.IO).launch {

                val userId = sessionManager.currentUserId.value!!

                val formattedQuestion = question.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }

                val aiRepository = factory.getRepository()
                val answer = aiRepository.getShortAnswer(formattedQuestion)
                val ask = Ask(
                    question = formattedQuestion,
                    userId = userId,
                    answer = answer,
                    createdAt = System.currentTimeMillis()
                )
                repository.addAsk(ask, userId)

                notificationHelper.showAnswerNotification(question, answer)
            }
        }

    @Provides
    @Singleton
    fun provideSpeechController(
        @ApplicationContext context: Context,
        detector: QuestionDetector,
        systemSoundController: SystemSoundController
    ): SpeechRecognizerController =
        AndroidSpeechRecognizer(context, detector, systemSoundController)
}
