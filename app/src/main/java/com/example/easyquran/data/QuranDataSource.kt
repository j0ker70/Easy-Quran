package com.example.easyquran.data

import com.example.easyquran.ui.chapters.ChapterListUIState
import kotlinx.coroutines.flow.Flow

interface QuranDataSource {

    fun getChapters(): Flow<ChapterListUIState>
}