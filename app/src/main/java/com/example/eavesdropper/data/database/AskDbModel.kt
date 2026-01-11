package com.example.eavesdropper.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ask_info")
data class AskDbModel(
    @PrimaryKey
    val id: Int,
    val question: String,
    val answer: String
)