package com.example.easyquran.model.dto

import com.example.easyquran.model.domain.Chapter
import com.google.gson.annotations.SerializedName

data class ChapterDto(
    @SerializedName("id")
    val id: Int? = 0,

    @SerializedName("name_simple")
    val name: String? = null,

    @SerializedName("revelation_place")
    val revelationPlace: String? = null,

    @SerializedName("translated_name")
    val translatedName: TranslatedNameDto?,

    @SerializedName("verses_count")
    val versesCount: Int? = 0
)

fun ChapterDto.toChapter() = Chapter(
    id = id ?: 0,
    name = name ?: "",
    translatedName = translatedName?.name ?: "",
    versesCount = versesCount ?: 0,
    revelationPlace = revelationPlace ?: "",
)