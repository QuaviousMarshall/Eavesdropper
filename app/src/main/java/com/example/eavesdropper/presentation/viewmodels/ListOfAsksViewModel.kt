package com.example.eavesdropper.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.usecases.TronUseCases
import com.example.eavesdropper.presentation.di.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfAsksViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val useCases: TronUseCases
) : ViewModel() {

    val asks: StateFlow<List<Ask>> =
        useCases.getAsksUseCase(sessionManager.currentUserId.value!!)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun delete(ask: Ask) {
        viewModelScope.launch {
            useCases.deleteAskUseCase(ask.id)
        }
    }
}



