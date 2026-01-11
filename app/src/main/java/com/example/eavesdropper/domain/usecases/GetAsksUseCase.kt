package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.repository.TronRepository

class GetAsksUseCase(
    private val repository: TronRepository
) {

    operator fun invoke() = repository.getAsks()
}