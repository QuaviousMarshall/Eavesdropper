package com.example.eavesdropper.data.detector

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.example.eavesdropper.domain.detector.QuestionDetector
import javax.inject.Inject

class AndroidSpeechRecognizer @Inject constructor(
    private val context: Context,
    private val detector: QuestionDetector,
    private val systemSoundController: SystemSoundController
) : SpeechRecognizerController {

    private var recognizer: SpeechRecognizer? = null

    private val handler = Handler(Looper.getMainLooper())

    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
    }

    private var isListening = false

    private val listener = object : RecognitionListener {

        override fun onResults(results: Bundle) {
            val text = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.firstOrNull()

            text?.let(detector::onText)

            restart()
        }

        override fun onError(error: Int) {
            restart()
        }

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    private fun createRecognizer() {
        recognizer = SpeechRecognizer
            .createSpeechRecognizer(context)
            .apply {
                setRecognitionListener(listener)
            }
    }

    override fun start() {
        if (isListening) return

        isListening = true
        systemSoundController.muteSystemSounds()

        createRecognizer()
        recognizer?.startListening(intent)
    }

    override fun stop() {
        if (!isListening) return

        isListening = false

        recognizer?.apply {
            try {
                stopListening()
                cancel()
                destroy()
            } catch (_: Exception) {
            }
        }

        recognizer = null

        systemSoundController.restoreSystemSounds()
    }

    override fun destroy() {
        stop()
    }

    private fun restart() {
        if (!isListening) return

        handler.postDelayed({
            try {
                recognizer?.startListening(intent)
            } catch (_: Exception) {
            }
        }, 300)
    }
}
