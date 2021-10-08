package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtpResponse(
    @SerializedName("otp")
    @Expose
    var otp: String? = null,

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null,

    @SerializedName("error")
    @Expose
    var error: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,
) : Parcelable