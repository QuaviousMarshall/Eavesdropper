package com.example.eavesdropper.data.detector

class QuestionDetectorImpl(
    private val onQuestionDetected: (String) -> Unit
) : QuestionDetector {

    override fun onText(text: String) {
        val normalized = text.lowercase().trim()

        val isQuestion =
            normalized.endsWith("?") ||
                    normalized.startsWith("как ") ||
                    normalized.startsWith("что ") ||
                    normalized.startsWith("почему ") ||
                    normalized.startsWith("зачем ") ||
                    normalized.startsWith("cколько ") ||
                    normalized.startsWith("где ") ||
                    normalized.startsWith("какая ") ||
                    normalized.startsWith("какой ") ||
                    normalized.startsWith("когда ") ||
                    normalized.startsWith("разве ") ||
                    normalized.startsWith("кому ")

        if (isQuestion) {
            onQuestionDetected(normalized.removeSuffix("?"))
        }
    }
}



