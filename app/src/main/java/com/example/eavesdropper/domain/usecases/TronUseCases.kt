package com.example.eavesdropper.domain.usecases

data class TronUseCases(
    val getAsksUseCase: GetAsksUseCase,
    val deleteAskUseCase: DeleteAskUseCase,
    val addAskUseCase: AddAskUseCase,
    val getDetailedAnswerUseCase: GetDetailedAnswerUseCase
)
