package com.example.eavesdropper.domain.repository

import androidx.lifecycle.LiveData
import com.example.eavesdropper.domain.entity.Ask

interface TronRepository {

    suspend fun deleteAsk(id: Int)

    suspend fun addAsk(ask: Ask)

    fun getAsks(): LiveData<List<Ask>>
}