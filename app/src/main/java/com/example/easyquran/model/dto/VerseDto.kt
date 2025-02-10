package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class Verse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("translations")
    val translations: List<TranslationDto>?,

    @SerializedName("verse_key")
    val verseKey: String?
)