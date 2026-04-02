package com.example.eavesdropper.domain.usecases

import com.example.eavesdropper.domain.repository.TronRepository
import javax.inject.Inject

class GetDetailedAnswerUseCase @Inject constructor(
    private val repository: TronRepository
) {
    suspend operator fun invoke(id: Int, answer: String) {
        repository.updateAnswer(id, answer)
    }
}