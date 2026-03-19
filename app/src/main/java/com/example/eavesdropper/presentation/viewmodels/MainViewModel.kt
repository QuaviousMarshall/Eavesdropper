package com.example.eavesdropper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import com.example.eavesdropper.data.local.preferences.PreferencesRepository
import com.example.eavesdropper.data.remote.model.AiModel
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.usecases.TronUseCases
import com.example.eavesdropper.presentation.di.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val tronUseCases: TronUseCases,
    private val speechController: SpeechRecognizerController,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val userId = sessionManager.currentUserId

    val n: StateFlow<Int> =
        preferencesRepository.getLastAsksCount(userId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                3
            )

    fun setNewDigitOfLastAsks(digit: Int) {
        viewModelScope.launch {
            preferencesRepository.saveLastAsksCount(userId, digit)
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
            preferencesRepository.setSelectedModel(userId, model)
        }
    }

    val aiModel: StateFlow<AiModel> =
        preferencesRepository.getSelectedModelFlow(userId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AiModel.GIGACHAT
            )

    val lastAsks: StateFlow<List<Ask>> =
        combine(
            tronUseCases.getAsksUseCase(userId),
            n
        ) { asks, count ->
            asks
                .sortedByDescending { it.id }
                .take(count)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}

