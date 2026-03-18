package com.example.eavesdropper.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface GigaChatApi {

    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body body: GigaChatRequest
    ): GigaChatResponse
}


