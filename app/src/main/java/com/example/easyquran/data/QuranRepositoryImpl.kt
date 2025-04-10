package com.example.easyquran.data

import com.example.model.Chapter
import com.example.network.data.QuranRemoteDataSource
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val dataSource: QuranRemoteDataSource
) : QuranRepository {

    override suspend fun getChapters() = dataSource.getChapters()

    override suspend fun getVersesForChapter(
        chapter: Chapter,
        page: Int
    ) = dataSource.getVersesForChapter(chapter, page)
}