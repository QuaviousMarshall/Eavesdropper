package com.example.eavesdropper.data.remote

import com.squareup.moshi.Json
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

data class GigaChatRequest(
    val model: String = "GigaChat",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class GigaChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class OAuthResponse(
    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "expires_at")
    val expiresAt: Long
)

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
            auth = "Basic MDE5Y2Y1YzMtZjFmYi03OTBhLWE1ZTMtNzM0NTlmYzQxZTk0OjE0YTRlZTI4LTMwN2QtNDY2OS04Nzk2LWY1NTIwMzIyMDA3MQ==",
            rqId = rqId,
            body = body
        )

        token = response.accessToken
        expiresAt = response.expiresAt

        return token!!
    }
}

class GigaChatAuthInterceptor @Inject constructor(
    private val tokenRepository: GigaChatTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            tokenRepository.getToken()
        }

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}



