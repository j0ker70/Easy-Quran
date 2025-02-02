package com.example.easyquran.data

import com.example.easyquran.ui.chapters.ChapterListUIState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val dataSource: QuranDataSource
) : QuranRepository {

    override fun getChapters(): Flow<ChapterListUIState> = dataSource.getChapters()
}