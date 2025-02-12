package com.example.easyquran.data

import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.ChapterVerse
import com.example.easyquran.utils.ApiResponse

interface QuranRepository {

    suspend fun getChapters(): ApiResponse<List<Chapter>>

    suspend fun getVersesForChapter(chapter: Chapter, page: Int): ApiResponse<ChapterVerse>
}