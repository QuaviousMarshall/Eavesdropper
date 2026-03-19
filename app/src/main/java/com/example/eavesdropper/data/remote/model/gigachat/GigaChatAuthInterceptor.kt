package com.example.eavesdropper.data.remote.model.gigachat

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

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