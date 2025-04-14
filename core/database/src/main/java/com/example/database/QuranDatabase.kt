package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.ChapterDao
import com.example.database.dao.VerseDao
import com.example.database.model.ChapterEntity
import com.example.database.model.VerseEntity

@Database(
    entities = [ChapterEntity::class, VerseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun chapterDao(): ChapterDao
    abstract fun verseDao(): VerseDao
}