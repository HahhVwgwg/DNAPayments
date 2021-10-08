package com.dnapayments.data.model

sealed class SimpleResult<out T> {

    data class Success<out T>(val data: T) : SimpleResult<T>()

    data class Error(val errorMessage: String) : SimpleResult<Nothing>()

    object NetworkError : SimpleResult<Nothing>()
}