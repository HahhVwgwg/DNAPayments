package com.dnapayments.data.model

class APIError {
    private val message: String? = null
    fun error(): String? {
        return message
    }
}