package com.dnapayments.data.model

class SampleResponseModal<T>(
    private val message: String,
    private val data: T? = null,
    private val status: Boolean,
    private val isUnauthenticated: Boolean,
) {

    fun getSimpleResult(): SimpleResult<T> {
        return if (status) {
            SimpleResult.Success(data!!)
        } else {
            SimpleResult.Error(message)
        }
    }
}
