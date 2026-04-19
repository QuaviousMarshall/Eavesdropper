package com.example.eavesdropper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import com.example.eavesdropper.data.local.preferences.PreferencesRepository
import com.example.eavesdropper.data.remote.model.AiModel
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.usecases.TronUseCases
import com.example.eavesdropper.data.di.SessionManager
import com.example.eavesdropper.domain.entity.Note
import com.example.eavesdropper.domain.repository.AuthRepository
import com.example.eavesdropper.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val tronUseCases: TronUseCases,
    private val repository: NoteRepository,
    private val authRepository: AuthRepository,
    private val speechController: SpeechRecognizerController,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val n: StateFlow<Int> =
        sessionManager.currentUserId
            .filterNotNull()
            .flatMapLatest { userId ->
                preferencesRepository.getLastAsksCount(userId)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                3
            )

    fun setNewDigitOfLastAsks(digit: Int) {
        viewModelScope.launch {
            val userId = sessionManager.currentUserId.value ?: return@launch
            preferencesRepository.saveLastAsksCount(userId, digit)
        }
    }

    fun sendOfferToUpdates(offer: String) {
        viewModelScope.launch {
            val userId = sessionManager.currentUserId.value ?: return@launch
            val note = Note(
                userId = userId,
                userLogin = authRepository.getUserInfo()?.email ?: "",
                offer = offer,
                createdAt = System.currentTimeMillis()
            )
            repository.addUpdatesOffer(
                note = note,
                userId = userId
            )
        }
    }

    private val _isTronEnabled = MutableStateFlow(false)
    val isTronEnabled: StateFlow<Boolean> = _isTronEnabled.asStateFlow()

    fun onTronButtonClick() {

        viewModelScope.launch {

            if (_isTronEnabled.value) {
                stopTron()
            } else {
                startTron()
            }

        }
    }

    private fun startTron() {
        speechController.start()
        _isTronEnabled.value = true
    }

    private fun stopTron() {
        speechController.stop()
        _isTronEnabled.value = false
    }

    fun setAiModel(model: AiModel) {
        viewModelScope.launch {
            val userId = sessionManager.currentUserId.value ?: return@launch
            preferencesRepository.setSelectedModel(userId, model)
        }
    }

    val aiModel: StateFlow<AiModel> =
        sessionManager.currentUserId
            .filterNotNull()
            .flatMapLatest { userId ->
                preferencesRepository.getSelectedModelFlow(userId)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AiModel.GIGACHAT
            )

    val lastAsks: StateFlow<List<Ask>> =
        sessionManager.currentUserId
            .filterNotNull()
            .flatMapLatest { userId ->
                combine(
                    tronUseCases.getAsksUseCase(userId),
                    n
                ) { asks, count ->
                    asks
                        .sortedByDescending { it.id }
                        .take(count)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}

