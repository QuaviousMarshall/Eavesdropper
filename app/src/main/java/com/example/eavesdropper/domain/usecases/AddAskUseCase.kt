package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository
import javax.inject.Inject

class AddAskUseCase @Inject constructor(
    private val repository: TronRepository
) {

    suspend operator fun invoke(ask: Ask, id: String) {
        repository.addAsk(ask, id)
    }
}