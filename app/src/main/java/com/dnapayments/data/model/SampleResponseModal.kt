package com.dnapayments.data.model

class SampleResponseModal<T>(
    private val error: String? = null,
    private val data: T? = null,
    private val id: Int? = null,
) {

    fun getSimpleResult(): SimpleResult<T> {
        println(this.id.toString())
        println(this.data.toString())
        error?.let {
            return SimpleResult.Error(it)
        } ?: return SimpleResult.Success(data!!)
    }
}

