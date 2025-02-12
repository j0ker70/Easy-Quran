package com.example.easyquran.model.domain

data class ChapterVerse(
    val chapterId: Int,
    val chapterName: String,
    val chapterTranslatedName: String,
    val chapterRevelationPlace: String,
    val canPaginate: Boolean,
    val verseList: List<Verse>
)