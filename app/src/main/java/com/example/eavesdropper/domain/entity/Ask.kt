package com.example.eavesdropper.domain.entity

data class Ask(
    val id: Int = 0,
    val userId: String,
    val question: String,
    val answer: String,
    val createdAt: Long
)