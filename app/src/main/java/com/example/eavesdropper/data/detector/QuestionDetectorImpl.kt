package com.example.eavesdropper.data.detector

import com.example.eavesdropper.domain.detector.QuestionDetector
import javax.inject.Inject

class QuestionDetectorImpl @Inject constructor(
    private val onQuestionDetected: (String) -> Unit
) : QuestionDetector {

    override fun onText(text: String) {
        val normalized = text.lowercase().trim()

        val questionStarters = listOf(
            "как", "что", "почему", "зачем", "cколько", "во cколько",
            "где", "какая", "куда", "какой", "когда", "разве", "кем", "чем", "кому",
            "кто", "кого", "о ком", "о чём", "чего", "чему", "какие", "какого", "какому",
            "каких", "насколько", "сколькими", "который", "которые", "которого", "которым",
            "откуда", "докуда", "с каких пор", "до каких пор", "при каких условиях",
            "по какой причине", "в каком случае", "неужели"
        )

        val isQuestion = normalized.endsWith("?") ||
                questionStarters.any { normalized.startsWith(it) }

        if (isQuestion) {
            onQuestionDetected(normalized.removeSuffix("?"))
        }

    }
}



