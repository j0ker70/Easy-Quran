package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class TranslationDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("text")
    val text: String?
)

fun TranslationDto.getText() = text ?: ""