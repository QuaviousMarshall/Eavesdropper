package com.example.eavesdropper.presentation.di

import com.example.eavesdropper.data.repository.TronRepositoryImpl
import com.example.eavesdropper.domain.repository.TronRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTronRepository(
        impl: TronRepositoryImpl
    ): TronRepository
}
