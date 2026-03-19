package com.example.eavesdropper.presentation.di

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private val auth = FirebaseAuth.getInstance()

    private val _currentUserId = MutableStateFlow<String?>(auth.currentUser?.uid)
    val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    private val authListener = FirebaseAuth.AuthStateListener {
        _currentUserId.value = it.currentUser?.uid
    }

    init {
        auth.addAuthStateListener(authListener)
    }
}
