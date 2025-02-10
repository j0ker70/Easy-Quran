package com.example.easyquran.model.domain

data class Verses(
    val chapterId: Int,
    val chapterName: String,
    val chapterTranslatedName: String,
    val chapterRevelationPlace: String,
    val verseList: List<String>
)