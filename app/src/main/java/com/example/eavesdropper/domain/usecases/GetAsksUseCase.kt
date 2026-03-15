package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAsksUseCase @Inject constructor(
    private val repository: TronRepository
) {

    operator fun invoke(userId: String): Flow<List<Ask>> {
        return repository.getAsks(userId)
    }
}