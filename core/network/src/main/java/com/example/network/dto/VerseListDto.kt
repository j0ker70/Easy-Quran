package com.example.network.dto

import com.google.gson.annotations.SerializedName

data class VerseListDto(
    @SerializedName("pagination")
    val pagination: PaginationDto?,

    @SerializedName("verses")
    val verses: List<VerseDto>?
)

fun VerseListDto.getVerseList() = verses?.map { it.getVerse() } ?: emptyList()