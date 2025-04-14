package com.example.easyquran.data

import com.example.model.Chapter
import com.example.model.ChapterVerse
import com.example.model.Verse
import com.example.network.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface QuranRepository {

    suspend fun getChaptersRemote(): ApiResponse<List<Chapter>>

    suspend fun getChaptersLocal(): Flow<List<Chapter>>

    suspend fun getVersesForChapter(chapter: Chapter, page: Int): ApiResponse<ChapterVerse>

    fun getAllVerses(chapter: Chapter): Flow<List<Verse>>
}