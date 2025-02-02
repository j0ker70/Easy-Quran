package com.example.easyquran.ui.chapters


sealed interface ChapterListUIState {

    data object Idle : ChapterListUIState

    data object Loading : ChapterListUIState

    data class Success(
        val chapterList: List<ChapterUI>
    ) : ChapterListUIState

    data class Failure(val errorMsg: String) : ChapterListUIState
}