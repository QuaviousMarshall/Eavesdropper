package com.example.eavesdropper.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eavesdropper.data.local.model.AskDbModel
import com.example.eavesdropper.data.local.model.NoteDbModel

@Database(entities = [AskDbModel::class, NoteDbModel::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                db = instance
                return instance
            }
        }

    }

    abstract fun askInfoDao(): AskDao

    abstract fun noteInfoDao(): NoteDao
}