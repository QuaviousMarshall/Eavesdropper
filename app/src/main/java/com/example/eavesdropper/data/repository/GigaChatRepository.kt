package com.example.eavesdropper.data.repository

import android.util.Log
import com.example.eavesdropper.data.remote.GigaChatApi
import com.example.eavesdropper.data.remote.GigaChatRequest
import com.example.eavesdropper.data.remote.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GigaChatRepository @Inject constructor(
    private val api: GigaChatApi
) {

    suspend fun getShortAnswer(question: String): String {
        return try {

            val prompt = """
            Ты — AI ассистент мобильного приложения.
            Отвечай строго одним кратким предложением (максимум 7 слов).

            Вопрос пользователя:
            $question
        """.trimIndent()

            val request = GigaChatRequest(
                model = "GigaChat",
                messages = listOf(
                    Message(
                        role = "user",
                        content = prompt
                    )
                )
            )

            val response = api.createChatCompletion(request)

            return response
                .choices
                .firstOrNull()
                ?.message
                ?.content
                ?: "Не удалось получить ответ"

        } catch (e: Exception) {
            Log.d("Gigachat", e.toString())
            "Ошибка сети"
        }
    }
}


