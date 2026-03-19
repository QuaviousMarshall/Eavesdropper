package com.example.eavesdropper.data.remote.model.openai

data class OpenAiResponseRequest(
    val model: String = "gpt-5-nano",
    val input: String
)
