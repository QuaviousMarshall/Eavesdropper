package com.example.eavesdropper.domain.repository

import com.example.eavesdropper.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getUpdates(): Flow<List<Note>>

    suspend fun addUpdatesOffer(note: Note, userId: String)
}