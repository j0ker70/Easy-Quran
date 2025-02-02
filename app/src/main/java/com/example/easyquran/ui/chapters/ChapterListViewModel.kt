package com.example.easyquran.ui.chapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
            quranRepository.getChapters().collect { uiState ->
                _chapterListState.update { uiState }
            }
        }
    }
}