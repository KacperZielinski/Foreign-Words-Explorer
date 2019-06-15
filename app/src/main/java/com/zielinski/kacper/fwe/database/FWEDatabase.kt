package com.zielinski.kacper.fwe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zielinski.kacper.fwe.domain.dao.WordDao
import com.zielinski.kacper.fwe.domain.model.Word

@Database(entities = [Word::class], version = 1)
abstract class FWEDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}