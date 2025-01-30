package com.example.easyquran.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.RetrofitInstance
import com.example.easyquran.model.dto.toChapters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChapterListViewModel : ViewModel() {

    private val _chapterListState = MutableStateFlow<ChapterListUIState>(ChapterListUIState.Idle)
    val chaptersListState: StateFlow<ChapterListUIState> get() = _chapterListState

    fun loadChapters() {
        viewModelScope.launch(Dispatchers.IO) {
            _chapterListState.update { ChapterListUIState.Loading }

            val chapters = RetrofitInstance.api.getChapters().body()!!.toChapters()
            _chapterListState.update { ChapterListUIState.Success(chapters) }
        }
    }
}

sealed interface ChapterListUIState {

    data object Idle : ChapterListUIState

    data object Loading : ChapterListUIState

    data class Success(
        val chapterList: List<ChapterUI>
    ) : ChapterListUIState

    data class Failure(val errorMsg: String) : ChapterListUIState
}