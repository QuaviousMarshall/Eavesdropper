package com.example.eavesdropper.domain.entity

data class Note(
    val id: Int = 0,
    val userId: String,
    val userLogin: String,
    val offer: String,
    val createdAt: Long
)
