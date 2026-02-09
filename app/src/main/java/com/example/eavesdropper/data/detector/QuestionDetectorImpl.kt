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
                    normalized.startsWith("во cколько ") ||
                    normalized.startsWith("где ") ||
                    normalized.startsWith("какая ") ||
                    normalized.startsWith("куда ") ||
                    normalized.startsWith("какой ") ||
                    normalized.startsWith("когда ") ||
                    normalized.startsWith("разве ") ||
                    normalized.startsWith("кем ") ||
                    normalized.startsWith("чем ") ||
                    normalized.startsWith("кому ")

        if (isQuestion) {
            onQuestionDetected(normalized.removeSuffix("?"))
        }
    }
}



