package com.example.eavesdropper.domain.repository

import com.example.eavesdropper.domain.entity.Ask
import kotlinx.coroutines.flow.Flow

interface TronRepository {

    suspend fun deleteAsk(id: Int)

    suspend fun addAsk(ask: Ask, userId: String)

    fun getAsks(userId: String): Flow<List<Ask>>

    suspend fun updateAnswer(id: Int, answer: String)
}
