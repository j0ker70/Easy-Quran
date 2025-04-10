package com.example.easyquran.data

import com.example.model.Chapter
import com.example.model.ChapterVerse
import com.example.network.utils.ApiResponse

interface QuranRepository {

    suspend fun getChapters(): ApiResponse<List<Chapter>>

    suspend fun getVersesForChapter(chapter: Chapter, page: Int): ApiResponse<ChapterVerse>
}