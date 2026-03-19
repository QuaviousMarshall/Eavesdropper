package com.example.eavesdropper.domain.repository

interface AiRepository {
    suspend fun getShortAnswer(question: String): String
}