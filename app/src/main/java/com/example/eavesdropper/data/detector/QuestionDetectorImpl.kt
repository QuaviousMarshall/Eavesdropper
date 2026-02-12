package com.example.eavesdropper.data.detector

import javax.inject.Inject

class QuestionDetectorImpl @Inject constructor(
    private val onQuestionDetected: (String) -> Unit
) : QuestionDetector {

    override fun onText(text: String) {
        val normalized = text.lowercase().trim()

        val questionStarters = listOf(
            "как", "что", "почему", "зачем", "cколько", "во cколько",
            "где", "какая", "куда", "какой", "когда", "разве", "кем", "чем", "кому"
        )

        val isQuestion = normalized.endsWith("?") ||
                questionStarters.any { normalized.startsWith(it) }

        if (isQuestion) {
            onQuestionDetected(normalized.removeSuffix("?"))
        }

    }
}



