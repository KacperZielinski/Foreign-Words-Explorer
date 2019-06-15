package com.zielinski.kacper.fwe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zielinski.kacper.fwe.domain.dao.WordDao
import com.zielinski.kacper.fwe.domain.model.Word

@Database(entities = [Word::class], version = 1)
abstract class FWEDatabase : RoomDatabase() {

    companion object {
        private const val FWE_DB_NAME = "FWE_DB"
        var instance: FWEDatabase? = null

        fun initialize(applicationContext: Context) {
            if (instance == null) {
                synchronized(FWEDatabase::class) {
                    instance = Room.databaseBuilder(applicationContext, FWEDatabase::class.java, FWE_DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
    }

    abstract fun wordDao(): WordDao
}