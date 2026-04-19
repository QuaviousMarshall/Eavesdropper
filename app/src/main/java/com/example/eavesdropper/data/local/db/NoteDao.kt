package com.example.eavesdropper.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eavesdropper.data.local.model.NoteDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_info ORDER BY createdAt DESC")
    fun getNotesList(): Flow<List<NoteDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NoteDbModel)
}