package com.dnapayments.data

import com.dnapayments.R

class Resource<T> private constructor(
    val status: Status,
    val data: T?,
    val message: Int = R.string.check_internet_connection,
    val errorMessage: String = "",
) {

    enum class Status {
        SUCCESS,
        ERROR,
        END_SESSION
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(msgId: Int = R.string.something_went_wrong): Resource<T> {
            return Resource(Status.ERROR, null, msgId)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, errorMessage = msg)
        }

        fun <T> endSession(): Resource<T> {
            return Resource(Status.END_SESSION, null)
        }

    }
}