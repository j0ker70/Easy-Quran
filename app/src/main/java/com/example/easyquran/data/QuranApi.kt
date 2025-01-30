package com.example.easyquran.data

import com.example.easyquran.model.dto.ChapterListDto
import retrofit2.Response
import retrofit2.http.GET

interface QuranApi {

    @GET("chapters")
    suspend fun getChapters(): Response<ChapterListDto>
}