package com.example.eavesdropper.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

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
}