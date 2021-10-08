package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageOtp(
    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("success")
    @Expose
    var success: String,

    @SerializedName("pending")
    @Expose
    var pending: String,

    @SerializedName("url")
    @Expose
    var url: String,

    @SerializedName("error")
    @Expose
    var error: String?,
) : Parcelable