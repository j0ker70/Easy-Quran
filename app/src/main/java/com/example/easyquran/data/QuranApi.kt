package com.example.easyquran.data

import com.example.easyquran.model.dto.ChapterListDto
import com.example.easyquran.model.dto.VerseListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApi {

    @GET("chapters")
    suspend fun getChapters(): Response<ChapterListDto>

    @GET("verses/by_chapter/{chapterID}?translations=en-taqi-usmani")
    suspend fun getVersesForChapter(
        @Path("chapterID") chapterID: Int,
        @Query("page") page: Int
    ): Response<VerseListDto>
}