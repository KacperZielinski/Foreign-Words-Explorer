package com.zielinski.kacper.fwe.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class FWEDatabaseImpl {
    companion object {
        private const val FWE_DB_NAME = "FWE_DB"

        lateinit var instance: RoomDatabase

        fun initialize(applicationContext: Context) {
            instance = Room.databaseBuilder(
                applicationContext,
                FWEDatabase::class.java, FWE_DB_NAME
            ).build()
        }
    }
}