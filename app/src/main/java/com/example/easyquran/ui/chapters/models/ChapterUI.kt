package com.example.easyquran.ui.chapters.models

import androidx.annotation.DrawableRes
import com.example.easyquran.R
import com.example.easyquran.domain.Chapter

data class ChapterUI(
    val id: String,
    val name: String,
    val translatedName: String,
    val versesCount: String,
    @DrawableRes val revelationPlaceIcon: Int
)

fun Chapter.toChapterUI() = ChapterUI(
    id = id.toString(),
    name = name,
    translatedName = translatedName,
    versesCount = "$versesCount verses",
    revelationPlaceIcon = if (revelationPlace == "makkah") R.drawable.makkah else R.drawable.madinah
)