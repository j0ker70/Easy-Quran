package com.example.easyquran.model.dto

import com.google.gson.annotations.SerializedName

data class PaginationDto(
    @SerializedName("current_page")
    val currentPage: Int?,

    @SerializedName("next_page")
    val nextPage: Int?,

    @SerializedName("per_page")
    val perPage: Int?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_records")
    val totalRecords: Int?
)