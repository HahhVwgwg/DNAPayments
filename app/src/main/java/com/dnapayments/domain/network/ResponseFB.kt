package com.dnapayments.domain.network

class ResponseFB<T>(
    private val success: Boolean = false,
    private val status: Boolean = false,
    private val message: String = "",
    val data: T? = null,
    val endSession: Boolean = false,
    private val msgType: String? = null,
) {

    fun getResponseFB(): SimpleResult<T> {
        return if (status) {
            SimpleResult.Success(data!!)
        } else if (!status) {
            SimpleResult.Error(message)
        } else {
            SimpleResult.NetworkError
        }
    }
}

