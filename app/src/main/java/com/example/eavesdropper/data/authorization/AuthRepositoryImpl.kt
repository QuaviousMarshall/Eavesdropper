package com.example.eavesdropper.data.authorization

import android.content.Context
import android.net.Uri
import com.example.eavesdropper.data.remote.CloudinaryApi
import com.example.eavesdropper.domain.entity.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val cloudinaryApi: CloudinaryApi,
    @ApplicationContext private val context: Context
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
            nickname = user.displayName,
            urlPhoto = user.photoUrl?.toString()
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

    override suspend fun uploadAvatar(uri: Uri) {

        val user = auth.currentUser ?: return

        val inputStream = context.contentResolver.openInputStream(uri)!!
        val bytes = inputStream.readBytes()

        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            "avatar.jpg",
            requestFile
        )

        val preset = "tronapp"
            .toRequestBody("text/plain".toMediaType())

        val response = cloudinaryApi.uploadImage(
            cloudName = "dehpzgsc8",
            file = multipartBody,
            uploadPreset = preset
        )

        val downloadUrl = response.secure_url.toUri()

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(downloadUrl)
            .build()

        user.updateProfile(profileUpdates).await()
        user.reload().await()
    }
}