package com.example.easyquran.model.dto

import com.example.easyquran.model.domain.Chapter
import com.google.gson.annotations.SerializedName

data class ChapterDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name_simple")
    val name: String,

    @SerializedName("revelation_place")
    val revelationPlace: String,

    @SerializedName("translated_name")
    val translatedName: TranslatedNameDto,

    @SerializedName("verses_count")
    val versesCount: Int
)

fun ChapterDto.toChapter() = Chapter(
    id = id,
    name = name,
    translatedName = translatedName.name,
    versesCount = versesCount,
    revelationPlace = revelationPlace
)


