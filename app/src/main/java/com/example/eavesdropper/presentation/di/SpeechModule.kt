package com.example.eavesdropper.presentation.di

import android.content.Context
import com.example.eavesdropper.data.detector.AndroidSpeechRecognizer
import com.example.eavesdropper.data.detector.QuestionDetector
import com.example.eavesdropper.data.detector.QuestionDetectorImpl
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import com.example.eavesdropper.data.detector.SystemSoundController
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
    fun provideQuestionDetector(
        repository: TronRepository
    ): QuestionDetector =
        QuestionDetectorImpl { question ->
            CoroutineScope(Dispatchers.IO).launch {
                repository.addAsk(
                    Ask(
                        id = question.hashCode(),
                        question = question,
                        answer = ""
                    )
                )
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
