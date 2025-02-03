package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class TranslatedNameDto(
    @SerializedName("language_name")
    val languageName: String? = null,

    @SerializedName("name")
    val name: String? = null
)