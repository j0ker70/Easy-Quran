package com.example.easyquran.data

import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.model.dto.getVerseList
import com.example.easyquran.model.dto.toChapters
import com.example.easyquran.utils.ApiResponse
import retrofit2.HttpException
import javax.inject.Inject

class QuranRemoteDataSourceImpl @Inject constructor(
    private val quranApi: QuranApi
) : QuranRemoteDataSource {

    override suspend fun getChapters(): ApiResponse<List<Chapter>> = try {
        val chapterResponse = quranApi.getChapters()

        if (chapterResponse.isSuccessful) {
            ApiResponse.Success(chapterResponse.body()?.toChapters() ?: emptyList())
        } else {
            ApiResponse.Failure("Server Error")
        }
    } catch (e: HttpException) {
        ApiResponse.Failure(e.message())
    } catch (e: Exception) {
        ApiResponse.Failure(e.toString())
    }

    override suspend fun getVersesForChapter(
        chapter: Chapter,
        page: Int
    ): ApiResponse<Verses> = try {
        val verseResponse = quranApi.getVersesForChapter(
            chapterID = chapter.id,
            translations = "en-taqi-usmani",
            page = page,
            perPage = 20
        )

        if (verseResponse.isSuccessful) {
            ApiResponse.Success(
                Verses(
                    chapterId = chapter.id,
                    chapterName = chapter.name,
                    chapterTranslatedName = chapter.translatedName,
                    chapterRevelationPlace = chapter.revelationPlace,
                    canPaginate = verseResponse.body()?.pagination?.nextPage != null,
                    verseList = verseResponse.body()?.getVerseList() ?: emptyList()
                )
            )
        } else {
            ApiResponse.Failure("Server Error")
        }
    } catch (e: HttpException) {
        ApiResponse.Failure(e.message())
    } catch (e: Exception) {
        ApiResponse.Failure(e.toString())
    }
}
