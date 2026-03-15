package com.example.eavesdropper.data.authorization

import com.example.eavesdropper.domain.entity.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
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

    override suspend fun resetPassword(email: String): Result<Unit> =
        runCatching {
            auth.sendPasswordResetEmail(email).await()
        }

    override fun getUserInfo(): UserInfo? {
        val user = auth.currentUser ?: return null
        val createdAt = user.metadata?.creationTimestamp ?: return null

        return UserInfo(
            email = user.email.orEmpty(),
            createdAt = createdAt,
            nickname = user.displayName
        )
    }

    override suspend fun addNickname(nickname: String) {
        val user = auth.currentUser ?: return

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(nickname)
            .build()

        user.updateProfile(profileUpdates).await()
        user.reload().await()
    }
}