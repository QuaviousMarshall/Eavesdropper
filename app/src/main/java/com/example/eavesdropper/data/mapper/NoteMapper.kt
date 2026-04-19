package com.example.eavesdropper.data.mapper

import com.example.eavesdropper.data.local.model.NoteDbModel
import com.example.eavesdropper.domain.entity.Note
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun mapDbModelToEntity(dbModel: NoteDbModel) = Note(
        id = dbModel.id,
        userId = dbModel.userId,
        userLogin = dbModel.userLogin,
        offer = dbModel.offer,
        createdAt = dbModel.createdAt
    )

    fun mapEntityToDbModel(note: Note) = NoteDbModel(
        id = note.id,
        userId = note.userId,
        userLogin = note.userLogin,
        offer = note.offer,
        createdAt = note.createdAt
    )
}