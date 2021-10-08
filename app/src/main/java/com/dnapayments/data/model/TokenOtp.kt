package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenOtp(
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null,
) : Parcelable