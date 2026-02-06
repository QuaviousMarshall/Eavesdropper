package com.example.eavesdropper.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ask_info")
data class AskDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val answer: String,
    val createdAt: Long
)