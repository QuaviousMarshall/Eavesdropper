package com.example.eavesdropper.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAiApi {

    @POST("responses")
    suspend fun createResponse(
        @Body body: OpenAiResponseRequest
    ): OpenAiResponse
}


