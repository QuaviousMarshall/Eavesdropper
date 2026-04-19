package com.example.eavesdropper.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eavesdropper.data.factory.AiRepositoryFactory
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.usecases.TronUseCases
import com.example.eavesdropper.data.di.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfAsksViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val useCases: TronUseCases,
    private val factory: AiRepositoryFactory
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private val loadingMap = mutableSetOf<Int>()

    val asks: StateFlow<List<Ask>> =
        sessionManager.currentUserId
            .filterNotNull()
            .flatMapLatest {userId ->
                useCases.getAsksUseCase(userId)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val filteredAsks: StateFlow<List<Ask>> =
        searchQuery
            .debounce(300)
            .combine(asks) { query, list ->
                if (query.isBlank()) list
                else list.filter {
                    it.question.contains(query, ignoreCase = true)
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun delete(ask: Ask) {
        viewModelScope.launch {
            useCases.deleteAskUseCase(ask.id)
        }
    }

    fun getDetailedAnswer(ask: Ask, prompt: String) {
        if (loadingMap.contains(ask.id)) return

        loadingMap.add(ask.id)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val aiRepository = factory.getRepository()
                val result = aiRepository.getFullAnswer(prompt)
                useCases.getDetailedAnswerUseCase(ask.id, result)
            } catch (e: Exception) {
                Log.e("AI", "Error: ${e.message}")
            } finally {
                loadingMap.remove(ask.id)
            }
        }
    }
}



