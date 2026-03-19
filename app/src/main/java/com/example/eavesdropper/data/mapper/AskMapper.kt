package com.example.eavesdropper.data.mapper

import com.example.eavesdropper.data.local.model.AskDbModel
import com.example.eavesdropper.domain.entity.Ask
import javax.inject.Inject

class AskMapper @Inject constructor() {

    fun mapDbModelToEntity(dbModel: AskDbModel) = Ask(
        id = dbModel.id,
        question = dbModel.question,
        answer = dbModel.answer,
        createdAt = dbModel.createdAt,
        userId = dbModel.userId
    )

    fun mapEntityToDbModel(ask: Ask) = AskDbModel(
        id = ask.id,
        question = ask.question,
        answer = ask.answer,
        createdAt = ask.createdAt,
        userId = ask.userId
    )
}