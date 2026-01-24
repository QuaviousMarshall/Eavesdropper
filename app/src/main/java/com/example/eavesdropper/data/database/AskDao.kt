package com.example.eavesdropper.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface AskDao {
    @Query("SELECT * FROM ask_info")
    fun getAsksList(): LiveData<List<AskDbModel>>

    @Query("DELETE FROM ask_info WHERE id = :askId")
    suspend fun deleteAsk(askId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsk(ask: AskDbModel)
}