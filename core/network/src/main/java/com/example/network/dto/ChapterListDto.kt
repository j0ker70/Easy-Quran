package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class ChapterListDto(
    @SerializedName("chapters")
    val chapters: List<ChapterDto>?
)

fun ChapterListDto.toChapters() = chapters?.map { it.toChapter() } ?: emptyList()