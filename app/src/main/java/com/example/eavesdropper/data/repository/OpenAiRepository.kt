package com.example.eavesdropper.data.repository

import android.util.Log
import com.example.eavesdropper.data.remote.OpenAiApi
import com.example.eavesdropper.data.remote.OpenAiResponseRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAiRepository @Inject constructor(
    private val api: OpenAiApi
) {

    suspend fun getShortAnswer(question: String): String {
        return try {

            val request = OpenAiResponseRequest(
                input = "Отвечай строго одним кратким предложением. $question"
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


