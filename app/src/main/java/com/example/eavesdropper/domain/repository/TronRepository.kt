package com.example.eavesdropper.domain.repository

import com.example.eavesdropper.domain.entity.Ask

interface TronRepository {

    fun getInfoAboutAccount()

    fun getAsks(): List<Ask>

    fun get3LastAsks(asks: List<Ask>): List<Ask>
}