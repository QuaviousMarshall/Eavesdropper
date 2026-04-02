package com.example.eavesdropper.presentation.di

import com.example.eavesdropper.domain.repository.TronRepository
import com.example.eavesdropper.domain.usecases.AddAskUseCase
import com.example.eavesdropper.domain.usecases.DeleteAskUseCase
import com.example.eavesdropper.domain.usecases.GetAsksUseCase
import com.example.eavesdropper.domain.usecases.GetDetailedAnswerUseCase
import com.example.eavesdropper.domain.usecases.TronUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideTronUseCases(repository: TronRepository): TronUseCases {
        return TronUseCases(
            GetAsksUseCase(repository),
            DeleteAskUseCase(repository),
            AddAskUseCase(repository),
            GetDetailedAnswerUseCase(repository)
        )
    }
}