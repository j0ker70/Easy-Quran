package com.example.network.data

import com.example.model.Chapter
import com.example.model.ChapterVerse
import com.example.network.utils.ApiResponse

interface QuranRemoteDataSource {

    suspend fun getChapters(): ApiResponse<List<Chapter>>

    suspend fun getVersesForChapter(chapter: Chapter, page: Int): ApiResponse<ChapterVerse>
}