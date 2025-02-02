package com.example.easyquran.data

import com.example.easyquran.model.dto.toChapters
import com.example.easyquran.ui.chapters.ChapterListUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

class QuranRemoteDataSource @Inject constructor(
    private val quranApi: QuranApi
) : QuranDataSource {

    override fun getChapters(): Flow<ChapterListUIState> = flow {
        emit(ChapterListUIState.Loading)

        try {
            val chapterResponse = quranApi.getChapters()

            if (chapterResponse.isSuccessful) {
                chapterResponse.body()?.let { chapters ->
                    emit(ChapterListUIState.Success(chapters.toChapters()))
                } ?: emit(ChapterListUIState.Failure("Server Error"))
            }
        } catch (e: UnresolvedAddressException) {
            emit(ChapterListUIState.Failure("No Internet"))
        } catch (e: Exception) {
            emit(ChapterListUIState.Failure("Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}