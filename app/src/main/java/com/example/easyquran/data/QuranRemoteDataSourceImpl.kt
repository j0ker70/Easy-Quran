package com.example.easyquran.data

import android.util.Log
import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.model.dto.Verse
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

    override suspend fun getVersesForChapter(chapter: Chapter): ApiResponse<Verses> {
        val versesText: MutableList<String> = emptyList<String>().toMutableList()

        var currentPage = 1
        while (true) {
            try {
                val verseResponse = quranApi.getVersesForChapter(chapter.id, currentPage)

                if (verseResponse.isSuccessful) {
                    val verseListDto = verseResponse.body()
                        ?: return ApiResponse.Failure("Null Response")
                    versesText.addAll(parseListFromDto(verseListDto.verses))
                    if (verseListDto.pagination?.nextPage == null) {
                        break
                    }
                    Log.i("pial", "pagination ${verseListDto.pagination}")
                } else {
                    return ApiResponse.Failure("Server Error")
                }
            } catch (e: HttpException) {
                return ApiResponse.Failure(e.message())
            } catch (e: Exception) {
                return ApiResponse.Failure(e.toString())
            }
            ++currentPage
        }

        return ApiResponse.Success(
            Verses(
                chapterId = chapter.id,
                chapterName = chapter.name,
                chapterTranslatedName = chapter.translatedName,
                chapterRevelationPlace = chapter.revelationPlace,
                verseList = versesText
            )
        )
    }

    private fun parseListFromDto(verses: List<Verse>?): List<String> {
        return verses?.map { verse ->
            verse.translations?.let {
                it[0].text ?: ""
            } ?: ""
        } ?: emptyList()
    }
}