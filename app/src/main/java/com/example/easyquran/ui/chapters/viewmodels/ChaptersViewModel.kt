package com.example.easyquran.ui.chapters.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyquran.data.RetrofitInstance
import com.example.easyquran.data.toChapters
import com.example.easyquran.ui.chapters.models.ChapterUI
import kotlinx.coroutines.launch

class ChaptersViewModel: ViewModel() {

    companion object {
        const val LOG_TAG = "ChaptersViewModel"
    }

    private val _chaptersList = MutableLiveData(emptyList<ChapterUI>())
    val chaptersList: LiveData<List<ChapterUI>> get() = _chaptersList

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadChapters()
    }

    private fun loadChapters() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val chapters = RetrofitInstance.api.getChapters().body()!!.toChapters()
                _chaptersList.value = chapters
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}