package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository

class AddAskUseCase(
    private val repository: TronRepository
) {

    suspend fun deleteAsk(ask: Ask) {
        return repository.addAsk(ask)
    }
}