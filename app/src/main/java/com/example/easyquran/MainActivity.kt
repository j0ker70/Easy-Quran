package com.example.easyquran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.easyquran.ui.chapters.ChapterListScreen
import com.example.easyquran.ui.theme.EasyQuranTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EasyQuranTheme {
                // TODO: move to navigation
                ChapterListScreen()
            }
        }
    }
}