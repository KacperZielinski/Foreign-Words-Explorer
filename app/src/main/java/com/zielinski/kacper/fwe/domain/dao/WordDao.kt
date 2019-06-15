package com.zielinski.kacper.fwe.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zielinski.kacper.fwe.domain.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM WORD")
    fun getAllWords(): List<Word>

    @Query("SELECT * FROM WORD WHERE WORD_EN = :word")
    fun findByTranslation(word: String): Word

    @Insert
    fun insertWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("DELETE FROM WORD")
    fun deleteAllWords()
}