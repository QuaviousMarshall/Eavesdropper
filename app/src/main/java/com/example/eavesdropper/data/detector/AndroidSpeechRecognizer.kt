package com.example.eavesdropper.data.detector

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import javax.inject.Inject

class AndroidSpeechRecognizer @Inject constructor(
    private val context: Context,
    private val detector: QuestionDetector,
    private val systemSoundController: SystemSoundController
) : SpeechRecognizerController {

    private val recognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
    }

    private var isListening = false

    init {
        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onResults(results: Bundle) {
                val text =
                    results.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )?.firstOrNull()

                text?.let(detector::onText)
                restart()
            }

            override fun onPartialResults(partialResults: Bundle) {
                val text =
                    partialResults.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )?.firstOrNull()

                text?.let(detector::onText)
            }

            override fun onError(error: Int) {
                restart()
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    override fun start() {
        if (!isListening) {
            isListening = true
            systemSoundController.muteSystemSounds()
            recognizer.startListening(intent)
        }
    }

    override fun stop() {
        isListening = false
        recognizer.stopListening()
        systemSoundController.restoreSystemSounds()
    }

    override fun destroy() {
        recognizer.destroy()
    }

    private fun restart() {
        if (isListening) {
            recognizer.cancel()
            recognizer.startListening(intent)
        }
    }
}
