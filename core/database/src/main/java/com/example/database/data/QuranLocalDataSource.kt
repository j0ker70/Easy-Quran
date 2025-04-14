package com.example.database.data

import com.example.model.Chapter
import com.example.model.Verse
import kotlinx.coroutines.flow.Flow

interface QuranLocalDataSource {

    fun getAllChapters(): Flow<List<Chapter>>

    suspend fun insertChapters(chapters: List<Chapter>)

    fun getVersesForChapter(chapterId: Int): Flow<List<Verse>>

    suspend fun insertVerses(verses: List<Verse>)
}