package com.example.easyquran.ui.verses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import com.example.easyquran.model.domain.Chapter
import com.example.easyquran.model.domain.Verses
import com.example.easyquran.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerseListViewModel @Inject constructor(
    private val quranRepository: QuranRepository
) : ViewModel() {

    private var _verseListState = MutableStateFlow<VerseListUIState>(VerseListUIState.Idle)
    val verseListState: StateFlow<VerseListUIState> get() = _verseListState

    private var currentPage = 1

    private var _canPaginate = MutableStateFlow(true)
    val canPaginate: StateFlow<Boolean> get() = _canPaginate

    fun loadNextVersesForChapter(chapter: Chapter) {
        if (!_canPaginate.value) return

        viewModelScope.launch(Dispatchers.IO) {
            if (_verseListState.value !is VerseListUIState.Success) {
                _verseListState.update { VerseListUIState.Loading }
            }

            when (val response = quranRepository.getVersesForChapter(chapter, currentPage)) {
                is ApiResponse.Success -> {
                    if (_verseListState.value is VerseListUIState.Success) {
                        _verseListState.update {
                            VerseListUIState.Success(
                                verses = getUpdatedVersesFroResponse(
                                    _verseListState.value as VerseListUIState.Success,
                                    response
                                )
                            )
                        }
                    } else {
                        _verseListState.update {
                            VerseListUIState.Success(
                                response.data.toVersesUI()
                            )
                        }
                    }

                    _canPaginate.update {
                        response.data.canPaginate
                    }
                }

                is ApiResponse.Failure -> _verseListState.update {
                    VerseListUIState.Failure(
                        response.errorMsg
                    )
                }
            }
            ++currentPage
        }
    }

    private fun getUpdatedVersesFroResponse(
        state: VerseListUIState.Success,
        response: ApiResponse.Success<Verses>
    ): VersesUI {
        return state.verses.copy(
            verseList = state.verses.verseList + response.data.verseList
        )
    }
}

sealed interface VerseListUIState {
    data object Idle : VerseListUIState

    data object Loading : VerseListUIState

    data class Success(
        val verses: VersesUI
    ) : VerseListUIState

    data class Failure(val errorMsg: String) : VerseListUIState
}