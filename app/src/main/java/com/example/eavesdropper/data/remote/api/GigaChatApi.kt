package com.example.eavesdropper.data.remote.api

import com.example.eavesdropper.data.remote.model.gigachat.GigaChatRequest
import com.example.eavesdropper.data.remote.model.gigachat.GigaChatResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GigaChatApi {

    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body body: GigaChatRequest
    ): GigaChatResponse
}


