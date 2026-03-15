package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.repository.TronRepository
import javax.inject.Inject

class DeleteAskUseCase @Inject constructor(
    private val repository: TronRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteAsk(id)
    }
}