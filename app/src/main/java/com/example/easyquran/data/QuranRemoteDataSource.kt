package com.example.easyquran.data

import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.utils.ApiResponse

interface QuranRemoteDataSource {

    suspend fun getChapters(): ApiResponse<List<Chapter>>

    suspend fun getVersesForChapter(chapter: Chapter): ApiResponse<Verses>
}