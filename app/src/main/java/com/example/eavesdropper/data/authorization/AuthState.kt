package com.example.eavesdropper.data.authorization

sealed interface AuthState {
    object Loading : AuthState
    object Authorized : AuthState
    object Unauthorized : AuthState
    data class Error(val message: String) : AuthState
}
