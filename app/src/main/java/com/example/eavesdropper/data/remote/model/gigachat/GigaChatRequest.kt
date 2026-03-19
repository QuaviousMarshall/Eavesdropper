package com.example.eavesdropper.data.remote.model.gigachat

data class GigaChatRequest(
    val model: String = "GigaChat",
    val messages: List<Message>
)
