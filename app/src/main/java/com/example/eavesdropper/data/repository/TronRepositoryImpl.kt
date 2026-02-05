package com.example.eavesdropper.data.repository

import com.example.eavesdropper.data.database.AskDao
import com.example.eavesdropper.data.mapper.AskMapper
import com.example.eavesdropper.domain.entity.Ask
import com.example.eavesdropper.domain.repository.TronRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TronRepositoryImpl @Inject constructor(
    private val askDao: AskDao,
    private val mapper: AskMapper
) : TronRepository {

    override fun getAsks(): Flow<List<Ask>> =
        askDao.getAsksList()
            .map { list ->
                list.map(mapper::mapDbModelToEntity)
            }

    override suspend fun addAsk(ask: Ask) {
        askDao.insertAsk(mapper.mapEntityToDbModel(ask))
    }

    override suspend fun deleteAsk(id: Int) {
        askDao.deleteAsk(id)
    }
}