package com.example.eavesdropper.data.remote.model.openai

data class OutputItem(
    val type: String?,
    val content: List<ContentItem>?,
    val status: String? = null
)