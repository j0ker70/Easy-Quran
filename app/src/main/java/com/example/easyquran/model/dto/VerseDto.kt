package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class VerseDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("verse_key")
    val verseKey: String?,

    @SerializedName("translations")
    val translations: List<TranslationDto>?
)

fun VerseDto.getTranslation() = translations?.get(0)?.getText() ?: ""