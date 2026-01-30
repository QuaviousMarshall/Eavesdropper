package com.example.eavesdropper.data.authorization

import com.example.eavesdropper.domain.entity.UserInfo

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    fun signOut()
    fun isAuthorized(): Boolean
    fun getUserInfo(): UserInfo?
}
