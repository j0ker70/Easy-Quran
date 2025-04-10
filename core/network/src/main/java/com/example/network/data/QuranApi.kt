package com.example.network.data

import com.example.network.dto.ChapterListDto
import com.example.network.dto.VerseListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApi {

    @GET("chapters")
    suspend fun getChapters(): Response<ChapterListDto>

    @GET("verses/by_chapter/{chapter_number}")
    suspend fun getVersesForChapter(
        @Path("chapter_number") chapterID: Int,
        @Query("fields") fields: String,
        @Query("translations") translations: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<VerseListDto>
}