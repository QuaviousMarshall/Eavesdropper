package com.example.eavesdropper.data.authorization

import com.example.eavesdropper.presentation.UserInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun isAuthorized(): Boolean =
        auth.currentUser != null

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getUserInfo(): UserInfo? {
        val user = auth.currentUser ?: return null
        val createdAt = user.metadata?.creationTimestamp ?: return null

        return UserInfo(
            email = user.email.orEmpty(),
            createdAt = createdAt
        )
    }
}