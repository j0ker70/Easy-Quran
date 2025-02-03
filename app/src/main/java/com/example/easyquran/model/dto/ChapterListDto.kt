package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class ChapterListDto(
    @SerializedName("chapters")
    val chapters: List<ChapterDto>?
)

fun ChapterListDto.toChapters() = chapters?.map { it.toChapter() } ?: emptyList()