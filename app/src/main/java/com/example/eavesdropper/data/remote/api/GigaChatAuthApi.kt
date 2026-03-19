package com.example.eavesdropper.data.remote.api

import com.example.eavesdropper.data.remote.model.gigachat.OAuthResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GigaChatAuthApi {

    @POST("api/v2/oauth")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: application/json"
    )
    suspend fun getToken(
        @Header("Authorization") auth: String,
        @Header("RqUID") rqId: String,
        @Body body: RequestBody
    ): OAuthResponse
}