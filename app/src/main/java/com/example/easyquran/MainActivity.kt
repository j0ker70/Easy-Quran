package com.example.easyquran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.easyquran.ui.chapters.ChaptersList
import com.example.easyquran.ui.chapters.viewmodels.ChaptersViewModel
import com.example.easyquran.ui.theme.EasyQuranTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: ChaptersViewModel by viewModels()

        setContent {
            val isLoading by viewModel.isLoading.observeAsState(true)
            val chapters by viewModel.chaptersList.observeAsState(emptyList())

            EasyQuranTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChaptersList(
                        chapters = chapters,
                        isLoading = isLoading,
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }
}