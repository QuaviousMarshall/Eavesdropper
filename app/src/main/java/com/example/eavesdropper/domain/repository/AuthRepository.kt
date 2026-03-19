package com.example.eavesdropper.domain.repository

import android.net.Uri
import com.example.eavesdropper.domain.entity.UserInfo

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
    fun signOut()
    fun isAuthorized(): Boolean
    fun getUserInfo(): UserInfo?
    suspend fun addNickname(nickname: String)
    suspend fun uploadAvatar(uri: Uri)
}