package com.example.eavesdropper.domain.entity

data class Ask(
    val id: Int = 0,
    val question: String,
    val answer: String,
    val createdAt: Long
)