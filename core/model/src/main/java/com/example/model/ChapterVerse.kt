package com.example.model

data class ChapterVerse(
    val chapterId: Int,
    val chapterName: String,
    val chapterTranslatedName: String,
    val chapterRevelationPlace: String,
    val canPaginate: Boolean,
    val verseList: List<Verse>
)