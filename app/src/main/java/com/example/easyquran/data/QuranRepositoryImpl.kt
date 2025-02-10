package com.example.easyquran.data

import com.example.easyquran.model.domain.Chapter
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val dataSource: QuranRemoteDataSource
) : QuranRepository {

    override suspend fun getChapters() = dataSource.getChapters()

    override suspend fun getVersesForChapter(chapter: Chapter) =
        dataSource.getVersesForChapter(chapter)
}