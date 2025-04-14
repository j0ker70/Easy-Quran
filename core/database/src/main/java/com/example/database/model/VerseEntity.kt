package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "verses",
    primaryKeys = ["chapter_id", "verse_id"],
    foreignKeys = [
        ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["id"],
            childColumns = ["chapter_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["chapter_id"])]
)
data class VerseEntity(
    @ColumnInfo(name = "chapter_id") val chapterId: Int,
    @ColumnInfo(name = "verse_id") val verseId: Int,
    @ColumnInfo(name = "arabic_text") val arabic: String,
    @ColumnInfo(name = "translated_text") val translated: String
)