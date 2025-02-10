package com.example.easyquran.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import com.example.easyquran.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterListViewModel @Inject constructor(
    private val quranRepository: QuranRepository
) : ViewModel() {

    private val _chapterListState = MutableStateFlow<ChapterListUIState>(ChapterListUIState.Idle)
    val chaptersListState: StateFlow<ChapterListUIState> get() = _chapterListState

    fun loadChapters() {
        viewModelScope.launch(Dispatchers.IO) {
            _chapterListState.update { ChapterListUIState.Loading }

            when (val response = quranRepository.getChapters()) {
                is ApiResponse.Success -> _chapterListState.update {
                    ChapterListUIState.Success(
                        response.data.map { it.toChapterUI() }
                    )
                }

                is ApiResponse.Failure -> _chapterListState.update {
                    ChapterListUIState.Failure(
                        response.errorMsg
                    )
                }
            }
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