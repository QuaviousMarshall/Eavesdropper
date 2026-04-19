package com.example.eavesdropper.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_info")
data class NoteDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val userLogin: String,
    val offer: String,
    val createdAt: Long
)
