package com.example.easyquran.data

import com.example.easyquran.model.domain.Chapter
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
}