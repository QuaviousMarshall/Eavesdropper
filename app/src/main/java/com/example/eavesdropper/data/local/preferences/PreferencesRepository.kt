package com.example.eavesdropper.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.eavesdropper.data.remote.model.AiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun aiModelKey(userId: String) =
        stringPreferencesKey("ai_model_$userId")

    private fun lastAsksKey(userId: String) =
        intPreferencesKey("last_asks_count_$userId")

    fun getLastAsksCount(userId: String): Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[lastAsksKey(userId)] ?: 3
        }

    suspend fun saveLastAsksCount(userId: String, count: Int) {
        context.dataStore.edit { preferences ->
            preferences[lastAsksKey(userId)] = count
        }
    }

    suspend fun setSelectedModel(userId: String, model: AiModel) {
        context.dataStore.edit { prefs ->
            prefs[aiModelKey(userId)] = model.name
        }
    }

    fun getSelectedModelFlow(userId: String): Flow<AiModel> =
        context.dataStore.data.map { prefs ->
            when (prefs[aiModelKey(userId)]) {
                AiModel.OPENAI.name -> AiModel.OPENAI
                AiModel.GIGACHAT.name -> AiModel.GIGACHAT
                else -> AiModel.GIGACHAT
            }
        }

    suspend fun getSelectedModel(userId: String): AiModel {
        val prefs = context.dataStore.data.first()

        return when (prefs[aiModelKey(userId)]) {
            AiModel.OPENAI.name -> AiModel.OPENAI
            AiModel.GIGACHAT.name -> AiModel.GIGACHAT
            else -> AiModel.GIGACHAT
        }
    }
}