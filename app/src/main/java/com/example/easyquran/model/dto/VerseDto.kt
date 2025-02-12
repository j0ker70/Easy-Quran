package com.example.easyquran.model.dto

import com.example.easyquran.model.domain.Verse
import com.google.gson.annotations.SerializedName

data class VerseDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("verse_key")
    val verseKey: String?,

    @SerializedName("text_indopak")
    val arabicVerse: String?,

    @SerializedName("translations")
    val translations: List<TranslationDto>?
)

fun VerseDto.getVerse() = Verse(
    verseKey = verseKey ?: "",
    arabic = arabicVerse ?: "",
    translated = translations?.get(0)?.getText() ?: ""
)