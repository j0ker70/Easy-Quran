package com.example.easyquran.ui.verses

import androidx.annotation.DrawableRes
import com.example.easyquran.utils.revelationPlaceStringToIcon
import com.example.model.ChapterVerse
import com.example.model.Verse

data class VersesUI(
    val chapterId: Int,
    val chapterName: String,
    val chapterTranslatedName: String,
    @DrawableRes val chapterRevelationPlaceIcon: Int,
    val canPaginate: Boolean,
    val verseList: List<Verse>
)

fun ChapterVerse.toVersesUI() = VersesUI(
    chapterId = chapterId,
    chapterName = chapterName,
    chapterTranslatedName = chapterTranslatedName,
    chapterRevelationPlaceIcon = revelationPlaceStringToIcon(chapterRevelationPlace),
    canPaginate = canPaginate,
    verseList = verseList
)