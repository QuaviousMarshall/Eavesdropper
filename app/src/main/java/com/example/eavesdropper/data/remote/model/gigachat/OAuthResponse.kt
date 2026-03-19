package com.example.eavesdropper.data.remote.model.gigachat

import com.squareup.moshi.Json

data class OAuthResponse(
    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "expires_at")
    val expiresAt: Long
)
