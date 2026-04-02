package com.example.eavesdropper.data.repository

import android.util.Log
import com.example.eavesdropper.data.remote.api.GigaChatApi
import com.example.eavesdropper.data.remote.model.gigachat.GigaChatRequest
import com.example.eavesdropper.data.remote.model.gigachat.Message
import com.example.eavesdropper.domain.repository.AiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GigaChatRepository @Inject constructor(
    private val api: GigaChatApi
): AiRepository {

    override suspend fun getShortAnswer(question: String): String {
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

    override suspend fun getFullAnswer(question: String): String {
        return try {

            val prompt = """
            Ты — AI ассистент мобильного приложения.
            Отвечай подробно, но ёмко. (максимум 60 слов).

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


