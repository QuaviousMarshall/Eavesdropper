package com.example.eavesdropper.data.remote.model.gigachat

import com.example.eavesdropper.data.remote.api.GigaChatAuthApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GigaChatTokenRepository @Inject constructor(
    private val authApi: GigaChatAuthApi
) {

    private var token: String? = null
    private var expiresAt: Long = 0

    suspend fun getToken(): String {

        val now = System.currentTimeMillis()

        if (token != null && now < expiresAt - 60000) {
            return token!!
        }

        val rqId = UUID.randomUUID().toString()

        val body = "scope=GIGACHAT_API_PERS"
            .toRequestBody("application/x-www-form-urlencoded".toMediaType())

        val response = authApi.getToken(
            auth = "Basic кукиш с маслом",
            rqId = rqId,
            body = body
        )

        token = response.accessToken
        expiresAt = response.expiresAt

        return token!!
    }
}



