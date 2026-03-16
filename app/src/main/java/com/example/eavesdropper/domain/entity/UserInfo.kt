package com.example.eavesdropper.domain.entity


data class UserInfo(
    val email: String,
    val createdAt: Long,
    val nickname: String?,
    val urlPhoto: String?
)