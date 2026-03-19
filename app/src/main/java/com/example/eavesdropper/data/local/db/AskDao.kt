package com.example.eavesdropper.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eavesdropper.data.local.model.AskDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AskDao {
    @Query("SELECT * FROM ask_info WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAsksList(userId: String): Flow<List<AskDbModel>>

    @Query("DELETE FROM ask_info WHERE id = :askId")
    suspend fun deleteAsk(askId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsk(ask: AskDbModel)
}