package com.example.easyquran.domain

data class Chapter(
    val id: Int,
    val name: String,
    val translatedName: String,
    val versesCount: Int,
    val revelationPlace: String
)
