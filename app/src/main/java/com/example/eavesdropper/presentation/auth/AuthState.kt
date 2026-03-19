package com.example.eavesdropper.presentation.auth

sealed interface AuthState {
    object Loading : AuthState
    object Authorized : AuthState
    object Unauthorized : AuthState
    data class Error(val message: String) : AuthState
}