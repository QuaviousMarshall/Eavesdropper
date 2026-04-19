package com.example.eavesdropper.data.di

import com.example.eavesdropper.data.repository.NoteRepositoryImpl
import com.example.eavesdropper.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteRepositoryModule {
    @Binds
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImpl
    ): NoteRepository
}
