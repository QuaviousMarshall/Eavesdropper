package com.example.eavesdropper.data.repository

import com.example.eavesdropper.data.local.db.NoteDao
import com.example.eavesdropper.data.mapper.NoteMapper
import com.example.eavesdropper.domain.entity.Note
import com.example.eavesdropper.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val mapper: NoteMapper,
    private val dao: NoteDao
) : NoteRepository {

    override fun getUpdates(): Flow<List<Note>> =
        dao.getNotesList()
            .map { list ->
                list.map(mapper::mapDbModelToEntity)
            }


    override suspend fun addUpdatesOffer(
        note: Note,
        userId: String
    ) {
        val dbModel = mapper.mapEntityToDbModel(note).copy(userId = userId)
        dao.addNote(dbModel)
    }
}