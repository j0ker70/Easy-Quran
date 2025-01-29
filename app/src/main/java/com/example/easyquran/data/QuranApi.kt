package com.example.easyquran.data

import retrofit2.Response
import retrofit2.http.GET

interface QuranApi {

    @GET("chapters")
    suspend fun getChapters(): Response<ChaptersListDto>
}