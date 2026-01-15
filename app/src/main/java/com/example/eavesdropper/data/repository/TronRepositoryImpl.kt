package com.example.eavesdropper.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.eavesdropper.data.database.AskDao
import com.example.eavesdropper.data.mapper.AskMapper
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository

class TronRepositoryImpl(
    private val askDao: AskDao,
    private val mapper: AskMapper
) : TronRepository {
    override suspend fun deleteAsk(id: Int) {
        askDao.deleteAsk(id)
    }

    override fun getAsks(): LiveData<List<Ask>> = askDao.getAsksList().map { listDbModels ->
        listDbModels.map { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }

    override suspend fun addAsk(ask: Ask) {
        val askToAdd = mapper.mapEntityToDbModel(ask)
        askDao.insertAsk(askToAdd)
    }
}