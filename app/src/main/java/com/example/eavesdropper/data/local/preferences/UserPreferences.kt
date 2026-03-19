package com.example.eavesdropper.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")