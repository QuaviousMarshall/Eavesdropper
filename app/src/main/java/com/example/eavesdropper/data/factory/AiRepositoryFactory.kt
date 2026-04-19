package com.example.eavesdropper.data.factory

import com.example.eavesdropper.data.local.preferences.PreferencesRepository
import com.example.eavesdropper.data.remote.model.AiModel
import com.example.eavesdropper.data.repository.GigaChatRepository
import com.example.eavesdropper.data.repository.OpenAiRepository
import com.example.eavesdropper.domain.repository.AiRepository
import com.example.eavesdropper.data.di.SessionManager
import javax.inject.Inject

class AiRepositoryFactory @Inject constructor(
    private val gigaChat: GigaChatRepository,
    private val openAi: OpenAiRepository,
    private val preferences: PreferencesRepository,
    private val sessionManager: SessionManager
) {

    suspend fun getRepository(): AiRepository {

        return when (preferences.getSelectedModel(sessionManager.currentUserId.value!!)) {
            AiModel.GIGACHAT -> gigaChat
            AiModel.OPENAI -> openAi
        }
    }
}