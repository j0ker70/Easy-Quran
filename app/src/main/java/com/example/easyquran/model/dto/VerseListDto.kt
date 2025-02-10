package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class VerseListDto(
    @SerializedName("pagination")
    val pagination: PaginationDto?,

    @SerializedName("verses")
    val verses: List<Verse>?
)