package com.example.eavesdropper.data.di

import android.content.Context
import androidx.room.Room
import com.example.eavesdropper.data.local.db.AppDatabase
import com.example.eavesdropper.data.local.db.AskDao
import com.example.eavesdropper.data.local.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "main.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAskDao(db: AppDatabase): AskDao =
        db.askInfoDao()

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao =
        db.noteInfoDao()
}
