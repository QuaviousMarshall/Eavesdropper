package com.example.eavesdropper.data.remote.api

import com.example.eavesdropper.data.remote.model.openai.OpenAiResponse
import com.example.eavesdropper.data.remote.model.openai.OpenAiResponseRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAiApi {

    @POST("responses")
    suspend fun createResponse(
        @Body body: OpenAiResponseRequest
    ): OpenAiResponse
}