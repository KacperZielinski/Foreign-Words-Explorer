package com.zielinski.kacper.fwe.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WORD")
data class Word (
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "WORD_PL")
    val wordPL: String?,

    @ColumnInfo(name = "WORD_EN")
    val wordEN: String?
)