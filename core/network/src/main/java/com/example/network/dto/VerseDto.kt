package com.example.network.dto

import com.example.model.Verse
import com.example.network.utils.getChapterAndVerseId
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

fun VerseDto.getVerse(): Verse {
    val (chapterId, verseId) = getChapterAndVerseId(verseKey)

    return Verse(
        chapterId = chapterId,
        verseId = verseId,
        arabic = arabicVerse ?: "",
        translated = translations?.get(0)?.getText() ?: ""
    )
}