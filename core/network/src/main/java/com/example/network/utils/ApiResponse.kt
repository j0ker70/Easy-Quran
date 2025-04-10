package com.example.network.utils

sealed class ApiResponse<out T> {

    data class Success<out T>(val data: T) : ApiResponse<T>()

    data class Failure(val errorMsg: String) : ApiResponse<Nothing>()
}