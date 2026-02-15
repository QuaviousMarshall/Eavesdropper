package com.example.eavesdropper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.data.detector.SpeechRecognizerController
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TronRepository,
    private val speechController: SpeechRecognizerController
) : ViewModel() {

    private val _isTronEnabled = MutableStateFlow(false)
    val isTronEnabled: StateFlow<Boolean> = _isTronEnabled.asStateFlow()

    private val _isTransitionInProgress = MutableStateFlow(false)
    val isTransitionInProgress: StateFlow<Boolean> = _isTransitionInProgress.asStateFlow()

    fun onTronButtonClick() {
        if (_isTransitionInProgress.value) return

        viewModelScope.launch {
            _isTransitionInProgress.value = true

            if (_isTronEnabled.value) {
                stopTron()
            } else {
                startTron()
            }

            _isTransitionInProgress.value = false
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

    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("User not authorized")


    val last3Asks: StateFlow<List<Ask>> =
        repository.getAsks(userId)
            .map { asks ->
                asks
                    .sortedByDescending { it.id }
                    .take(3)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
}
