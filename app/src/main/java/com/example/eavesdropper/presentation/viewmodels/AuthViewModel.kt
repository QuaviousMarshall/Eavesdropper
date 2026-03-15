package com.example.eavesdropper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.data.authorization.AuthRepository
import com.example.eavesdropper.data.authorization.AuthState
import com.example.eavesdropper.domain.entity.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    init {
        checkAuth()
        loadUserInfo()
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return repository.resetPassword(email)
    }

    private fun checkAuth() {
        _state.value = if (repository.isAuthorized()) {
            AuthState.Authorized
        } else {
            AuthState.Unauthorized
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            repository.signIn(email, password)
                .onSuccess {
                    _state.value = AuthState.Authorized
                    _userInfo.value = repository.getUserInfo()
                }
                .onFailure { _state.value = AuthState.Error("Неверный логин или пароль") }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            repository.signUp(email, password)
                .onSuccess { _state.value = AuthState.Authorized }
        }
    }

    fun logout() {
        repository.signOut()
        _state.value = AuthState.Unauthorized
    }

    fun resetState() {
        _state.value = AuthState.Unauthorized
    }

    private fun loadUserInfo() {
        _userInfo.value = repository.getUserInfo()
    }

    fun addNickname(nickname: String) {
        viewModelScope.launch {
            repository.addNickname(nickname)
            loadUserInfo()
        }
    }
}
