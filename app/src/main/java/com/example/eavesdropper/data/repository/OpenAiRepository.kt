package com.example.eavesdropper.data.repository

import android.util.Log
import com.example.eavesdropper.data.remote.api.OpenAiApi
import com.example.eavesdropper.data.remote.model.openai.OpenAiResponseRequest
import com.example.eavesdropper.domain.repository.AiRepository
import jakarta.inject.Singleton
import javax.inject.Inject

@Singleton
class OpenAiRepository @Inject constructor(
    private val api: OpenAiApi
): AiRepository {

    override suspend fun getShortAnswer(question: String): String {
        return try {

            val request = OpenAiResponseRequest(
                input = "Отвечай строго одним кратким предложением (максимум 7 слов). $question"
            )

            val response = api.createResponse(request)

            response.output
                ?.firstOrNull {it.type == "message"}
                ?.content
                ?.firstOrNull { it.type == "output_text" }
                ?.text
                ?.trim()
                ?: "Нет ответа"

        } catch (e: Exception) {
            Log.d("OpenAI", e.toString())
            "Ошибка сети"
        }
    }
}