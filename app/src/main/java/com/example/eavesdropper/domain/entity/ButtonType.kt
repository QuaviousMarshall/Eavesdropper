package com.example.eavesdropper.domain.entity

data class ButtonItem(
    val type: ButtonType
)

enum class ButtonType {
    ABOUTACCOUNT, ABOUTAPP, LOGOUTACCOUNT
}