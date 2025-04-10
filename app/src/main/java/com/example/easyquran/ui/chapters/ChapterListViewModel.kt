package com.example.easyquran.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import com.example.network.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterListViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _chapterListState = MutableStateFlow<ChapterListUIState>(ChapterListUIState.Idle)
    val chaptersListState = _chapterListState.asStateFlow()

    fun loadChapters() {
        viewModelScope.launch(dispatcher) {
            _chapterListState.update { ChapterListUIState.Loading }

            _chapterListState.update {
                when (val response = quranRepository.getChapters()) {
                    is ApiResponse.Success -> {
                        ChapterListUIState.Success(response.data.map { it.toChapterUI() })
                    }

                    is ApiResponse.Failure -> ChapterListUIState.Failure(response.errorMsg)
                }
            }
        }
    }
}

sealed interface ChapterListUIState {
    data object Idle : ChapterListUIState
    data object Loading : ChapterListUIState
    data class Success(val chapterList: List<ChapterUI>) : ChapterListUIState
    data class Failure(val errorMsg: String) : ChapterListUIState
}