package com.example.easyquran.ui.verses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.ChapterVerse
import com.example.easyquran.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerseListViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _verseListState = MutableStateFlow<VerseListUIState>(VerseListUIState.Idle)
    val verseListState = _verseListState.asStateFlow()

    private var _canPaginate = MutableStateFlow(true)
    val canPaginate = _canPaginate.asStateFlow()

    private var currentPage = 1

    fun loadNextVersesForChapter(chapter: Chapter) {
        if (!_canPaginate.value) return

        viewModelScope.launch(dispatcher) {
            if (_verseListState.value !is VerseListUIState.Success) {
                _verseListState.update { VerseListUIState.Loading }
            }

            _verseListState.update {
                when (val response = quranRepository.getVersesForChapter(chapter, currentPage)) {
                    is ApiResponse.Success -> {
                        _canPaginate.update {
                            response.data.canPaginate
                        }

                        if (_verseListState.value is VerseListUIState.Success) {
                            VerseListUIState.Success(
                                verses = getUpdatedVersesFromResponse(
                                    _verseListState.value as VerseListUIState.Success,
                                    response
                                )
                            )
                        } else {
                            VerseListUIState.Success(
                                response.data.toVersesUI()
                            )
                        }
                    }

                    is ApiResponse.Failure -> {
                        VerseListUIState.Failure(response.errorMsg)
                    }
                }
            }
            ++currentPage
        }
    }

    private fun getUpdatedVersesFromResponse(
        state: VerseListUIState.Success,
        response: ApiResponse.Success<ChapterVerse>
    ): VersesUI {
        return state.verses.copy(
            verseList = state.verses.verseList + response.data.verseList
        )
    }
}

sealed interface VerseListUIState {
    data object Idle : VerseListUIState
    data object Loading : VerseListUIState
    data class Success(val verses: VersesUI) : VerseListUIState
    data class Failure(val errorMsg: String) : VerseListUIState
}