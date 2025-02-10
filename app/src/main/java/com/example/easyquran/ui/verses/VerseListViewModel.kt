package com.example.easyquran.ui.verses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import com.example.easyquran.model.domain.Chapter
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

    fun loadVerseForChapter(chapter: Chapter) {
        viewModelScope.launch(Dispatchers.IO) {
            _verseListState.update { VerseListUIState.Loading }

            when (val response = quranRepository.getVersesForChapter(chapter)) {
                is ApiResponse.Success -> _verseListState.update {
                    VerseListUIState.Success(
                        response.data.toVersesUI()
                    )
                }

                is ApiResponse.Failure -> _verseListState.update {
                    VerseListUIState.Failure(
                        response.errorMsg
                    )
                }
            }
        }
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