package com.example.eavesdropper.data.remote

data class OpenAiResponseRequest(
    val model: String = "gpt-5-nano",
    val input: String
)


data class OpenAiResponse(
    val output: List<OutputItem>?
)

data class OutputItem(
    val type: String?,
    val content: List<ContentItem>?,
    val status: String? = null
)


data class ContentItem(
    val type: String?,
    val text: String?
)