package com.example.easyquran.ui.chapters

import androidx.annotation.DrawableRes
import com.example.easyquran.utils.revelationPlaceIconToString
import com.example.easyquran.utils.revelationPlaceStringToIcon
import com.example.model.Chapter
import kotlinx.serialization.Serializable

@Serializable
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
    revelationPlaceIcon = revelationPlaceStringToIcon(revelationPlace)
)

fun ChapterUI.toChapter() = Chapter(
    id = id.toInt(),
    name = name,
    translatedName = translatedName,
    versesCount = versesCount.takeIf { it.isEmpty() }?.split(" ")?.first()?.toInt() ?: 0,
    revelationPlace = revelationPlaceIconToString(revelationPlaceIcon)
)