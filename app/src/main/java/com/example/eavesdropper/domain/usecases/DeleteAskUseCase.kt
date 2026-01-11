package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.repository.TronRepository

class DeleteAskUseCase(
    private val repository: TronRepository
) {

    suspend fun deleteAsk(id: Int) {
        return repository.deleteAsk(id)
    }
}