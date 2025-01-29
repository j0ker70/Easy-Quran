package com.example.easyquran.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: QuranApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.quran.com/api/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuranApi::class.java)
    }
}