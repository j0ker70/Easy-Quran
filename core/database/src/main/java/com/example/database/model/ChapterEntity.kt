package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "translated_name") val translatedName: String,
    @ColumnInfo(name = "verses_count") val versesCount: Int,
    @ColumnInfo(name = "revelation_place") val revelationPlace: String
)