package com.example.easyquran.data

import com.example.database.data.QuranLocalDataSource
import com.example.model.Chapter
import com.example.model.ChapterVerse
import com.example.network.data.QuranRemoteDataSource
import com.example.network.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuranRemoteDataSource,
    private val localDataSource: QuranLocalDataSource
) : QuranRepository {

    override suspend fun getChaptersRemote(): ApiResponse<List<Chapter>> {
        val remoteResponse = remoteDataSource.getChapters()

        if (remoteResponse is ApiResponse.Success) {
            localDataSource.insertChapters(remoteResponse.data)
        }

        return remoteResponse
    }

    override suspend fun getChaptersLocal(): Flow<List<Chapter>> = localDataSource.getAllChapters()

    override suspend fun getVersesForChapter(
        chapter: Chapter,
        page: Int
    ): ApiResponse<ChapterVerse> {
        val remoteResponse = remoteDataSource.getVersesForChapter(chapter, page)

        if (remoteResponse is ApiResponse.Success) {
            localDataSource.insertVerses(remoteResponse.data.verseList)
        }

        return remoteResponse
    }

    override fun getAllVerses(chapter: Chapter) =
        localDataSource.getVersesForChapter(chapterId = chapter.id)
}