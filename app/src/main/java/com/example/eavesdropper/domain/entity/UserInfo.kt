package com.example.eavesdropper.domain.entity

import android.media.Image

data class UserInfo(
    val email: String,
    val createdAt: Long,
    val nickname: String? = null,
    val icon: Image? = null
)