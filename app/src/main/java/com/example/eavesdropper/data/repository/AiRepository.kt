package com.example.eavesdropper.data.repository

interface AiRepository {
    suspend fun getShortAnswer(question: String): String
}